/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package MVC.model.transaction;

import MVC.model.blockchain.Block;
import MVC.model.blockchain.Blockchain;
import MVC.model.store.RocksDBUtils;
import MVC.model.utils.SerializeUtils;
import com.google.common.collect.Maps;
import lombok.Synchronized;
import org.apache.commons.codec.binary.Hex;
import org.apache.commons.lang3.ArrayUtils;

import java.util.Map;

/**
 *
 * @author ssopt
 */

public class UTXOSet {
    private Blockchain blockchain;

    public UTXOSet(Blockchain blockchain) {
        this.blockchain = blockchain;
    }

    public UTXOSet() {
    }

    // tìm giao dịch để tri tiêu
    public SpendableOutputResult findSpendableOutputs(byte[] pubKeyHash, int amount) {
        Map<String, int[]> unspentOuts = Maps.newHashMap();
        int accumulated = 0;
        Map<String, byte[]> chainstateBucket = RocksDBUtils.getInstance().getChainstateBucket();
        for (Map.Entry<String, byte[]> entry : chainstateBucket.entrySet()) {
            String txId = entry.getKey();
            TXOutput[] txOutputs = (TXOutput[]) SerializeUtils.deserialize(entry.getValue());

            for (int outId = 0; outId < txOutputs.length; outId++) {
                TXOutput txOutput = txOutputs[outId];
                if (txOutput.isLockedWithKey(pubKeyHash) && accumulated < amount) {
                    accumulated += txOutput.getValue();

                    int[] outIds = unspentOuts.get(txId);
                    if (outIds == null) {
                        outIds = new int[]{outId};
                    } else {
                        outIds = ArrayUtils.add(outIds, outId);
                    }
                    unspentOuts.put(txId, outIds);
                    if (accumulated >= amount) {
                        break;
                    }
                }
            }
        }
        return new SpendableOutputResult(accumulated, unspentOuts);
    }
    
    // tim tat ca UTXO tuong ung voi dia chi Vi
    public TXOutput[] findUTXOs(byte[] pubKeyHash) {
        TXOutput[] utxos = {};
        Map<String, byte[]> chainstateBucket = RocksDBUtils.getInstance().getChainstateBucket();
        if (chainstateBucket.isEmpty()) {
            return utxos;
        }
        for (byte[] value : chainstateBucket.values()) {
            TXOutput[] txOutputs = (TXOutput[]) SerializeUtils.deserialize(value);
            for (TXOutput txOutput : txOutputs) {
                if (txOutput.isLockedWithKey(pubKeyHash)) {
                    utxos = ArrayUtils.add(utxos, txOutput);
                }
            }
        }
        return utxos;
    }
    // xây dựng ại chỉ mục nhóm UTXO
    @Synchronized
    public void reIndex() {
        RocksDBUtils.getInstance().cleanChainStateBucket();
        Map<String, TXOutput[]> allUTXOs = blockchain.findAllUTXOs();
        for (Map.Entry<String, TXOutput[]> entry : allUTXOs.entrySet()) {
            RocksDBUtils.getInstance().putUTXOs(entry.getKey(), entry.getValue());
        }
    }
    
    // update UTXO
    // khi 1 block mới được tạo cần làm 2 việc : 
    // 1. xuất output đã chi tiêu ra khỏi UTXO
    // 2. Lưu UTXO mới.
    
    @Synchronized
    public void update(Block tipBlock) {
        // tipBlock khối mới nhất
        if (tipBlock == null) {
            throw new RuntimeException("Fail to update UTXO set ! ");
        }
        for (Transaction transaction : tipBlock.getTransactions()) {

            // tìm các outputTX còn lại chưa giao dịch
            if (!transaction.isCoinbase()) {
                for (TXInput txInput : transaction.getInputs()) {
                    // inputTX chua dc su dung con lai
                    TXOutput[] remainderUTXOs = {};
                    String txId = Hex.encodeHexString(txInput.getTxId());
                    TXOutput[] txOutputs = RocksDBUtils.getInstance().getUTXOs(txId);

                    if (txOutputs == null) {
                        continue;
                    }

                    for (int outIndex = 0; outIndex < txOutputs.length; outIndex++) {
                        if (outIndex != txInput.getTxOutputIndex()) {
                            remainderUTXOs = ArrayUtils.add(remainderUTXOs, txOutputs[outIndex]);
                        }
                    }

                    // xoa neu khong  con, neu con thi update
                    if (remainderUTXOs.length == 0) {
                        RocksDBUtils.getInstance().deleteUTXOs(txId);
                    } else {
                        RocksDBUtils.getInstance().putUTXOs(txId, remainderUTXOs);
                    }
                }
            }

            //output moi duoc luu vao DB
            TXOutput[] txOutputs = transaction.getOutputs();
            String txId = Hex.encodeHexString(transaction.getTxId());
            RocksDBUtils.getInstance().putUTXOs(txId, txOutputs);
        }

    }
}
