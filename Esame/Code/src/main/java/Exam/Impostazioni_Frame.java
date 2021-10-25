package Exam;

import javax.swing.*;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.TreeCellRenderer;
import java.awt.*;
import java.net.URL;
import java.util.Objects;

public class Impostazioni_Frame extends JFrame {

    public Impostazioni_Frame() {

        super("Impostazioni");


        setBounds(200,200,550,400);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout(20,20));


        Toolkit kit = Toolkit.getDefaultToolkit();
        Image image = kit.createImage(Objects.requireNonNull(this.getClass().getResource("/rotella.png")));
        setIconImage(image);

        //create the root node
        DefaultMutableTreeNode root = new DefaultMutableTreeNode("Impostazioni");

        //create the child nodes
        DefaultMutableTreeNode LoginNode = new DefaultMutableTreeNode("Login");
        Leaf[] leaves = new Leaf[]{
                new Leaf("Aggiungi/Elimina User","/user.png"),
                new Leaf("Cambia Password","/lucchetto.png")
        };

        for (Leaf leaf : leaves){
            DefaultMutableTreeNode node =new DefaultMutableTreeNode(leaf);
            LoginNode.add(node);
        }
        DefaultMutableTreeNode SportNode = new DefaultMutableTreeNode("Sport");
        SportNode.add(new DefaultMutableTreeNode("Modifica Sport"));
        DefaultMutableTreeNode StruttureNode = new DefaultMutableTreeNode("Strutture");
        StruttureNode.add(new DefaultMutableTreeNode("Modifica Strutture"));
        //add the child nodes to the root node
        root.add(LoginNode);
        root.add(SportNode);
        root.add(StruttureNode);

        JTree tree = new JTree(root);
        tree.setCellRenderer( new LeafRenderer());

        add(tree,BorderLayout.WEST);

        setLocationRelativeTo(null);
        setVisible(true);
        setAlwaysOnTop(true);

    }
}

class LeafRenderer implements TreeCellRenderer {
    private final JLabel label;

    LeafRenderer() {
        label = new JLabel();
    }

    public Component getTreeCellRendererComponent(JTree tree, Object value, boolean selected, boolean expanded,
                                                  boolean leaf1, int row, boolean hasFocus) {
        Object mia =  ((DefaultMutableTreeNode) value).getUserObject();
        if (mia instanceof Leaf) {
            URL imageUrl = getClass().getResource(((Leaf) mia).getLeafIcon());
            if (imageUrl != null) {
                ImageIcon icon = new ImageIcon(new ImageIcon(imageUrl).getImage().getScaledInstance(18,18,Image.SCALE_DEFAULT));
                label.setIcon(icon);
            }
            label.setText(((Leaf)mia).getName());
        }
        else {
            label.setIcon(null);
            label.setText(value.toString());
        }
        return label;
    }
}

 class Leaf {
    private final String name;
    private final String leafIcon;

    Leaf(String name, String leafIcon) {
        this.name = name;
        this.leafIcon = leafIcon;
    }

    public String getName() {
        return name;
    }
    public String getLeafIcon() {
        return leafIcon;
    }

}
