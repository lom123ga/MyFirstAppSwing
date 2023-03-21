/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package MVC.model.store;

import MVC.model.blockchain.Block;
import MVC.model.transaction.TXOutput;
import MVC.model.utils.SerializeUtils;
import com.google.common.collect.Maps;
import org.rocksdb.RocksDB;
import org.rocksdb.RocksDBException;

import java.util.Map;

/**
 *
 * @author ssopt
 */
public class RocksDBUtils {
    // data blockchain
    private static final String DB_FILE = "blockchain.db";
    
    private static final String BLOCKS_BUCKET_KEY = "blocks";
    
    private static final String CHAINSTATE_BUCKET_KEY = "chainstate";
    
    private static final String LAST_BLOCK_KEY = "l";
    
    private volatile static RocksDBUtils instance;
    
    public static RocksDBUtils getInstance() {
        if (instance == null) {
            synchronized (RocksDBUtils.class) {
                if (instance == null) {
                    instance = new RocksDBUtils();
                }
            }
        }
        return instance;
    }
    
    private RocksDB db;
    private Map<String, byte[]> blocksBucket;
    private Map<String, byte[]> chainstateBucket;
    
    private RocksDBUtils() {
        openDB();
        initBlockBucket();
        initChainStateBucket();
    }
    
    private void openDB() {
        try {
            db = RocksDB.open(DB_FILE);
        } catch (RocksDBException e) {
            throw new RuntimeException("Fail to open db ! ", e);
        }
    }
    
    private void initBlockBucket() {
        try {
            byte[] blockBucketKey = SerializeUtils.serialize(BLOCKS_BUCKET_KEY);
            byte[] blockBucketBytes = db.get(blockBucketKey);
            if (blockBucketBytes != null) {
                blocksBucket = (Map) SerializeUtils.deserialize(blockBucketBytes);
            } else {
                blocksBucket = Maps.newHashMap();
                db.put(blockBucketKey, SerializeUtils.serialize(blocksBucket));
            }
        } catch (RocksDBException e) {
            
            throw new RuntimeException("Fail to init block bucket ! ", e);
        }
    }
    
    private void initChainStateBucket() {
        try {
            byte[] chainstateBucketKey = SerializeUtils.serialize(CHAINSTATE_BUCKET_KEY);
            byte[] chainstateBucketBytes = db.get(chainstateBucketKey);
            if (chainstateBucketBytes != null) {
                chainstateBucket = (Map) SerializeUtils.deserialize(chainstateBucketBytes);
            } else {
                chainstateBucket = Maps.newHashMap();
                db.put(chainstateBucketKey, SerializeUtils.serialize(chainstateBucket));
            }
        } catch (RocksDBException e) {
            
            throw new RuntimeException("Fail to init chainstate bucket ! ", e);
        }
    }
    
    public void putLastBlockHash(String tipBlockHash) {
        try {
            blocksBucket.put(LAST_BLOCK_KEY, SerializeUtils.serialize(tipBlockHash));
            db.put(SerializeUtils.serialize(BLOCKS_BUCKET_KEY), SerializeUtils.serialize(blocksBucket));
        } catch (RocksDBException e) {
          
            throw new RuntimeException("Fail to put last block hash ! tipBlockHash=" + tipBlockHash, e);
        }
    }
    
    public String getLastBlockHash() {
        byte[] lastBlockHashBytes = blocksBucket.get(LAST_BLOCK_KEY);
        if (lastBlockHashBytes != null) {
            return (String) SerializeUtils.deserialize(lastBlockHashBytes);
        }
        return "";
    }
    
    public void putBlock(Block block) {
        try {
            blocksBucket.put(block.getHash(), SerializeUtils.serialize(block));
            db.put(SerializeUtils.serialize(BLOCKS_BUCKET_KEY), SerializeUtils.serialize(blocksBucket));
        } catch (RocksDBException e) {
           
            throw new RuntimeException("Fail to put block ! block=" + block.toString(), e);
        }
    }
    
    public Block getBlock(String blockHash) {
        byte[] blockBytes = blocksBucket.get(blockHash);
        if (blockBytes != null) {
            return (Block) SerializeUtils.deserialize(blockBytes);
        }
        throw new RuntimeException("Fail to get block ! blockHash=" + blockHash);
    }
    
    public void cleanChainStateBucket() {
        try {
            chainstateBucket.clear();
        } catch (Exception e) {
         
            throw new RuntimeException("Fail to clear chainstate bucket ! ", e);
        }
    }
    
    public void putUTXOs(String key, TXOutput[] utxos) {
        try {
            chainstateBucket.put(key, SerializeUtils.serialize(utxos));
            db.put(SerializeUtils.serialize(CHAINSTATE_BUCKET_KEY), SerializeUtils.serialize(chainstateBucket));
        } catch (Exception e) {
      
            throw new RuntimeException("Fail to put UTXOs into chainstate bucket ! key=" + key, e);
        }
    }
    
    public TXOutput[] getUTXOs(String key) {
        byte[] utxosByte = chainstateBucket.get(key);
        if (utxosByte != null) {
            return (TXOutput[]) SerializeUtils.deserialize(utxosByte);
        }
        return null;
    }
    
    public void deleteUTXOs(String key) {
        try {
            chainstateBucket.remove(key);
            db.put(SerializeUtils.serialize(CHAINSTATE_BUCKET_KEY), SerializeUtils.serialize(chainstateBucket));
        } catch (Exception e) {
            
            throw new RuntimeException("Fail to delete UTXOs by key ! key=" + key, e);
        }
    }
    
    public void closeDB() {
        try {
            db.close();
        } catch (Exception e) {
            
            throw new RuntimeException("Fail to close db ! ", e);
        }
    }

    public Map<String, byte[]> getBlocksBucket() {
        return this.blocksBucket;
    }

    public Map<String, byte[]> getChainstateBucket() {
        return this.chainstateBucket;
    }
}
