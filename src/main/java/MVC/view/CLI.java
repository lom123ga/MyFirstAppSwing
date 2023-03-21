/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package MVC.view;


import MVC.model.blockchain.Block;
import MVC.model.blockchain.Blockchain;
import MVC.model.pow.ProofOfWork;
import MVC.model.store.RocksDBUtils;
import MVC.model.transaction.TXOutput;
import MVC.model.transaction.Transaction;
import MVC.model.transaction.UTXOSet;
import MVC.model.utils.Base58Check;
import MVC.model.wallet.Wallet;
import MVC.model.wallet.WalletUtils;

import java.io.UnsupportedEncodingException;
import java.util.Arrays;
import java.util.Set;
import org.apache.commons.cli.*;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;

/**
 *
 * @author ssopt
 */


public class CLI {
    private String[] args;
    private Options options = new Options();

    public CLI(String[] args) {
        this.args = args;

        Option helpCmd = Option.builder("h").desc("show help").build();
        options.addOption(helpCmd);
 
        Option address = Option.builder("address").hasArg(true).desc("Source wallet address").build();
        Option sendFrom = Option.builder("from").hasArg(true).desc("Source wallet address").build();
        Option sendTo = Option.builder("to").hasArg(true).desc("Destination wallet address").build();
        Option sendAmount = Option.builder("amount").hasArg(true).desc("Amount to send").build();

        options.addOption(address);
        options.addOption(sendFrom);
        options.addOption(sendTo);
        options.addOption(sendAmount);
    }

  
    public void parse() {
        this.validateArgs(args);
        try {
            CommandLineParser parser = new DefaultParser();
            CommandLine cmd = parser.parse(options, args);
            switch (args[0]) {
                case "createblockchain":
                    String createblockchainAddress = cmd.getOptionValue("address");

                    if (StringUtils.isBlank(createblockchainAddress)) {
                        help();
                    }
                    this.createBlockchain(createblockchainAddress);
                    break;
                case "mineblock":
                    String minerAddress = cmd.getOptionValue("address");
                    String tx = args[3];
                    minBlock(minerAddress,tx);
                    break;

                case "getbalance":
                    String getBalanceAddress = cmd.getOptionValue("address");

                    if (StringUtils.isBlank(getBalanceAddress)) {
                        help();
                    }
                    this.getBalance(getBalanceAddress);
                    break;

                case "getdata":
                    String dataAddres = cmd.getOptionValue("address");

                    this.findBlockData(dataAddres);
                    break;
                case "send":
                    String sendFrom = cmd.getOptionValue("from");
                    String sendTo = cmd.getOptionValue("to");
                    String sendAmount = cmd.getOptionValue("amount");
                    if (StringUtils.isBlank(sendFrom) ||
                            StringUtils.isBlank(sendTo) ||
                            !NumberUtils.isDigits(sendAmount)) {
                        help();
                    }
                    this.send(sendFrom, sendTo, Integer.valueOf(sendAmount));
                    break;
                case "createwallet":
                    this.createWallet();
                    break;
                case "printaddresses":
                    this.printAddresses();
                    break;
                case "printchain":
                    this.printChain();
                    break;
                case "h":
                    this.help();
                    break;
                default:
                    this.help();
            }
        } catch (Exception e) {
            System.out.println("Fail to parse cli command ! ");
        } finally {
            RocksDBUtils.getInstance().closeDB();
        }
    }

    private void findBlockData(String address) throws UnsupportedEncodingException {
        Blockchain blockchain = Blockchain.initBlockchainFromDB();
        for (Blockchain.BlockchainIterator iterator = blockchain.getBlockchainIterator(); iterator.hashNext(); ) {
            Block block = iterator.next();
            if (block != null) {
                if (block.getHash().equals(address)){
                    System.out.println(new String(block.getTransactions()[0].getInputs()[0].getPubKey(),"US-ASCII"));
                }
            }
        }
        System.out.println("Fail to get Data!");
    }

    private void validateArgs(String[] args) {
        if (args == null || args.length < 1) {
            help();
        }
    }

    private void minBlock(String from, String tx){
        Blockchain blockchain = Blockchain.createBlockchain(from);
        Transaction rewardTx = Transaction.newCoinbaseTX(from, tx);
        Block newBlock = blockchain.mineBlock(new Transaction[]{rewardTx});
        new UTXOSet(blockchain).update(newBlock);
        System.out.println("Success!");
    }
  
    private void createBlockchain(String address) {
        Blockchain blockchain = Blockchain.createBlockchain(address);
        UTXOSet utxoSet = new UTXOSet(blockchain);
        utxoSet.reIndex();
        System.out.println("Done ! ");
    }

   
    private void createWallet() throws Exception {
        Wallet wallet = WalletUtils.getInstance().createWallet();
        System.out.println("wallet address : " + wallet.getAddress());
    }

    private void printAddresses() {
        Set<String> addresses = WalletUtils.getInstance().getAddresses();
        if (addresses == null || addresses.isEmpty()) {
            System.out.println("There isn't address");
            return;
        }
        for (String address : addresses) {
            System.out.println("Wallet address: " + address);
        }
    }

   
    private void getBalance(String address) {
       
        try {
            Base58Check.base58ToBytes(address);
        } catch (Exception e) {
            throw new RuntimeException("ERROR: invalid wallet address", e);
        }

        byte[] versionedPayload = Base58Check.base58ToBytes(address);
        byte[] pubKeyHash = Arrays.copyOfRange(versionedPayload, 1, versionedPayload.length);

        Blockchain blockchain = Blockchain.createBlockchain(address);
        UTXOSet utxoSet = new UTXOSet(blockchain);

        TXOutput[] txOutputs = utxoSet.findUTXOs(pubKeyHash);
        int balance = 0;
        if (txOutputs != null && txOutputs.length > 0) {
            for (TXOutput txOutput : txOutputs) {
                balance += txOutput.getValue();
            }
        }
        System.out.printf("Balance of  %s :  %d TNTCoin" , address, balance);
    }

    
    private void send(String from, String to, int amount) throws Exception {
      
        try {
            Base58Check.base58ToBytes(from);
        } catch (Exception e) {
            //log.error("ERROR: sender address invalid ! address=" + from, e);
            throw new RuntimeException("ERROR: sender address invalid ! address=" + from, e);
        }
     
        try {
            Base58Check.base58ToBytes(to);
        } catch (Exception e) {
            //log.error("ERROR: receiver address invalid ! address=" + to, e);
            throw new RuntimeException("ERROR: receiver address invalid ! address=" + to, e);
        }
        if (amount < 0) {
            //log.error("ERROR: amount invalid ! amount=" + amount);
            throw new RuntimeException("ERROR: amount invalid ! amount=" + amount);
        }
        Blockchain blockchain = Blockchain.createBlockchain(from);
     
        Transaction transaction = Transaction.newUTXOTransaction(from, to, amount, blockchain);
      
        Transaction rewardTx = Transaction.newCoinbaseTX(from, "");
        Block newBlock = blockchain.mineBlock(new Transaction[]{transaction, rewardTx});
        new UTXOSet(blockchain).update(newBlock);
        System.out.println("Success!");
    }

    private void help() {
        System.out.println("Usage:");
        System.out.println("  createwallet - Generates a new key-pair and saves it into the wallet file");
        System.out.println("  printaddresses - print all wallet address");
        System.out.println("  getbalance -address ADDRESS - Get balance of ADDRESS");
        System.out.println("  createblockchain -address ADDRESS - Create a blockchain ");
        System.out.println("  mineblock -address ADDRESS - Create a block and send mine block reward to ADDRESS");
        System.out.println("  printchain - Print all the blocks of the blockchain");
        System.out.println("  send -from FROM -to TO -amount AMOUNT - Send AMOUNT of coins from FROM address to TO");
        System.out.println("  getdata -address ADDRESS - get data ");
        System.exit(0);
    }

   
    private void printChain() {
        Blockchain blockchain = Blockchain.initBlockchainFromDB();
        for (Blockchain.BlockchainIterator iterator = blockchain.getBlockchainIterator(); iterator.hashNext(); ) {
            Block block = iterator.next();
            if (block != null) {
                boolean validate = ProofOfWork.newProofOfWork(block).validate();
                System.out.println(block.toString() + ", validate = " + validate);
            }
        }
    }
}
