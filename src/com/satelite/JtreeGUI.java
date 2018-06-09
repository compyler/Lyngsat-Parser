package com.satelite;
import javax.swing.*;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.text.StyledDocument;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreePath;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

 class JtreeGUI extends JFrame {



    DefaultTreeModel tree_model;
    JSplitPane splitter;
    JTree tree;
    JEditorPane display;

    DefaultMutableTreeNode channel = new DefaultMutableTreeNode("Channels");
    SateliteDataBase db = new SateliteDataBase();

    public JtreeGUI(List<TVChannel> tvChannels) {

        super("Lyngsat-Parser");
        setSize(500,300);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new BorderLayout());
        setLocationRelativeTo(null);



        tree_model = new DefaultTreeModel(channel);
        tree = new JTree(tree_model);



        //create channel nodes
        for (TVChannel tvc : tvChannels) {

            String chn = tvc.getName();
            DefaultMutableTreeNode chanNode = new DefaultMutableTreeNode(chn);
            channel.add(chanNode);

        }


        JScrollPane treescroll = new JScrollPane(tree);
        display = new JEditorPane();
        display.setEditable(false);
        JScrollPane displayscroll = new JScrollPane(display);
        splitter = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT);
        splitter.setLeftComponent(treescroll);
        splitter.setRightComponent(displayscroll);
        splitter.setDividerLocation(160);


        setVisible(true);
        add(splitter);
        tree.addTreeSelectionListener(new SelectionListener());

    }


    class SelectionListener implements TreeSelectionListener{
        public void valueChanged(TreeSelectionEvent se){
            DefaultMutableTreeNode selected_node = (DefaultMutableTreeNode) tree.getLastSelectedPathComponent();
            String selected_node_name = selected_node.toString();


            int nodeLevel = selected_node.getPath().length;
            if (3 == nodeLevel){
                display.setText(db.getSateliteDescription(selected_node_name));

            } else if (2 == nodeLevel) {

                List<Satelite> sats = db.getSatsForChannelName(selected_node_name);

                System.out.print(selected_node_name);

                for (Satelite s : sats) {
                    String satelite = s.getName();

                    DefaultMutableTreeNode satNode = new DefaultMutableTreeNode(satelite);
                    selected_node.add(satNode);
                }

                display.setText(db.getChannelDescription(selected_node_name));
            }
        }
    }
}



