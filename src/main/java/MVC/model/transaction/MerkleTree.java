/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package MVC.model.transaction;

import MVC.model.utils.ByteUtils;
import com.google.common.collect.Lists;
import org.apache.commons.codec.digest.DigestUtils;

import java.util.List;

/**
 *
 * @author ssopt
 */
public class MerkleTree {
    private Node root;
    
    private byte[][] leafHashes;

    public MerkleTree(Node root, byte[][] leafHashes) {
        this.root = root;
        this.leafHashes = leafHashes;
    }

    public MerkleTree() {
    }

    public Node getRoot() {
        return this.root;
    }

    public byte[][] getLeafHashes() {
        return this.leafHashes;
    }

    public void setRoot(Node root) {
        this.root = root;
    }

    public void setLeafHashes(byte[][] leafHashes) {
        this.leafHashes = leafHashes;
    }

    public boolean equals(final Object o) {
        if (o == this) return true;
        if (!(o instanceof MerkleTree)) return false;
        final MerkleTree other = (MerkleTree) o;
        if (!other.canEqual((Object) this)) return false;
        final Object this$root = this.getRoot();
        final Object other$root = other.getRoot();
        if (this$root == null ? other$root != null : !this$root.equals(other$root)) return false;
        if (!java.util.Arrays.deepEquals(this.getLeafHashes(), other.getLeafHashes())) return false;
        return true;
    }

    protected boolean canEqual(final Object other) {
        return other instanceof MerkleTree;
    }

    public int hashCode() {
        final int PRIME = 59;
        int result = 1;
        final Object $root = this.getRoot();
        result = result * PRIME + ($root == null ? 43 : $root.hashCode());
        result = result * PRIME + java.util.Arrays.deepHashCode(this.getLeafHashes());
        return result;
    }

    public String toString() {
        return "MerkleTree(root=" + this.getRoot() + ", leafHashes=" + java.util.Arrays.deepToString(this.getLeafHashes()) + ")";
    }

    public static class Node {
        private byte[] hash;
        private Node left;
        private Node right;

        public Node() {
        }

        public byte[] getHash() {
            return this.hash;
        }

        public Node getLeft() {
            return this.left;
        }

        public Node getRight() {
            return this.right;
        }

        public void setHash(byte[] hash) {
            this.hash = hash;
        }

        public void setLeft(Node left) {
            this.left = left;
        }

        public void setRight(Node right) {
            this.right = right;
        }

        public boolean equals(final Object o) {
            if (o == this) return true;
            if (!(o instanceof Node)) return false;
            final Node other = (Node) o;
            if (!other.canEqual((Object) this)) return false;
            if (!java.util.Arrays.equals(this.getHash(), other.getHash())) return false;
            final Object this$left = this.getLeft();
            final Object other$left = other.getLeft();
            if (this$left == null ? other$left != null : !this$left.equals(other$left)) return false;
            final Object this$right = this.getRight();
            final Object other$right = other.getRight();
            if (this$right == null ? other$right != null : !this$right.equals(other$right)) return false;
            return true;
        }

        protected boolean canEqual(final Object other) {
            return other instanceof Node;
        }

        public int hashCode() {
            final int PRIME = 59;
            int result = 1;
            result = result * PRIME + java.util.Arrays.hashCode(this.getHash());
            final Object $left = this.getLeft();
            result = result * PRIME + ($left == null ? 43 : $left.hashCode());
            final Object $right = this.getRight();
            result = result * PRIME + ($right == null ? 43 : $right.hashCode());
            return result;
        }

        public String toString() {
            return "MerkleTree.Node(hash=" + java.util.Arrays.toString(this.getHash()) + ", left=" + this.getLeft() + ", right=" + this.getRight() + ")";
        }
    }
    
    // creat a node
    private static Node constructLeafNode(byte[] hash) {
        Node leaf = new Node();
        leaf.hash = hash;
        return leaf;
    }
    
    public MerkleTree(byte[][] leafHashes) {
        constructTree(leafHashes);
    }
    
    private byte[] internalHash(byte[] leftChildHash, byte[] rightChildHash) {
        byte[] mergedBytes = ByteUtils.merge(leftChildHash, rightChildHash);
        return DigestUtils.sha256(mergedBytes);
    }
    
    // creat a parent node
    private Node constructInternalNode(Node leftChild, Node rightChild) {
        Node parent = new Node();
        if (rightChild == null) {
            parent.hash = leftChild.hash;
        } else {
            parent.hash = internalHash(leftChild.hash, rightChild.hash);
        }
        parent.left = leftChild;
        parent.right = rightChild;
        return parent;
    }
    
    
    private List<Node> bottomLevel(byte[][] hashes) {
        List<Node> parents = Lists.newArrayListWithCapacity(hashes.length / 2);

        for (int i = 0; i < hashes.length - 1; i += 2) {
            Node leaf1 = constructLeafNode(hashes[i]);
            Node leaf2 = constructLeafNode(hashes[i + 1]);

            Node parent = constructInternalNode(leaf1, leaf2);
            parents.add(parent);
        }

        if (hashes.length % 2 != 0) {
            Node leaf = constructLeafNode(hashes[hashes.length - 1]);
            Node parent = constructInternalNode(leaf, leaf);
            parents.add(parent);
        }

        return parents;
    }
    
    private void constructTree(byte[][] leafHashes) {
        if (leafHashes == null || leafHashes.length < 1) {
            throw new RuntimeException("ERROR:Fail to construct merkle tree ! leafHashes data invalid ! ");
        }
        this.leafHashes = leafHashes;
        List<Node> parents = bottomLevel(leafHashes);
        while (parents.size() > 1) {
            parents = internalLevel(parents);
        }
        root = parents.get(0);
    }

    private List<Node> internalLevel(List<Node> children) {
        List<Node> parents = Lists.newArrayListWithCapacity(children.size() / 2);
        for (int i = 0; i < children.size() - 1; i += 2) {
            Node child1 = children.get(i);
            Node child2 = children.get(i + 1);

            Node parent = constructInternalNode(child1, child2);
            parents.add(parent);
        }
        
        if (children.size() % 2 != 0) {
            Node child = children.get(children.size() - 1);
            Node parent = constructInternalNode(child, null);
            parents.add(parent);
        }

        return parents;
    }
}
