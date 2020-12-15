package gameClient;

import api.*;
import gameClient.util.Point3D;
import gameClient.util.Range;
import gameClient.util.Range2D;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import java.util.Iterator;
import java.util.List;

/**
 * This class represents a very simple GUI class to present a
 * game on a graph - you are welcome to use this class - yet keep in mind
 * that the code is not well written in order to force you improve the
 * code and not to take it "as is".
 */
public class MyFrame_Gui extends JFrame implements Serializable, MouseListener {
    private BufferedImage getPokemon, getgrpah, getAgent;
    private game_service gameServer;
    private int mWin_h = 10;
    private int mWin_w = 10;
    private Graphics2D g2D;

    private Graphics graphics;

    private Arena _ar;
    private gameClient.util.Range2Range _w2f;


    public MyFrame_Gui(String a) {
        super(a);
        resize();
        this.setLayout(null);

        setTitle("The Challenge Pokemon");


    }

    public void resize() {


        setSize(1000, 750);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setVisible(true);
        getContentPane().addComponentListener(new ComponentAdapter() {
            public void componentresizble(ComponentEvent e) {
                Component c = (Component) e.getSource();

            }


        });
    }

    public MyFrame_Gui() {

    }


    public void update(Arena ar) {
        this._ar = ar;
        updateFrame();
    }

    private void updateFrame() {
        Range rx = new Range(20, this.getWidth() - 20);
        Range ry = new Range(this.getHeight() - 10, 150);
        Range2D frame = new Range2D(rx, ry);
        directed_weighted_graph g = _ar.getGraph();
        _w2f = Arena.w2f(g, frame);
    }

    public void paint(Graphics g) {
        int w = mWin_w;
        int h = mWin_h;
        //  g.clearRect(0, 0, w, h);
        //    Image image =createImage(w,h);

        //paintComponents(g);

        try {
            this.getSize();
            BufferedImage image1 = ImageIO.read(new File("doc/background.png"));
            g.drawImage(image1, 0, 0, null);
        } catch (IOException e) {
            e.printStackTrace();
        }

        paintComponents(g);
        //    paintComponents(g);
        Image image = createImage(w, h);
        Graphics graphics = image.getGraphics();
        paintComponents(graphics);

        g.drawImage(image, 0, 0, this);


        //	updateFrame();


    }

    @Override
    public void paintComponents(Graphics g) {
        // super.paintComponents(g);
        drawPokemon(g);
        drawInfo1(g);
        drawGraph(g);
        drawAgents(g);
        drawInfo(g);
        drawA(g);
    }

    void drawInfo(Graphics g) {

        if (_ar != null) {
            List<String> str = _ar.get_info();
            String dt = "none";
            for (int i = 0; i < str.size(); i++) {
                g.drawString(str.get(i) + " dt: " + dt, 100, 60 + i * 20);
            }


        }
    }

    void drawGraph(Graphics g) {
        if (_ar != null) {
            directed_weighted_graph gg = _ar.getGraph();
            Iterator<node_data> iter = gg.getV().iterator();
            while (iter.hasNext()) {
                node_data n = iter.next();
                g.setColor(Color.BLUE);
                drawNode(n, 5, g);
                Iterator<edge_data> itr = gg.getE(n.getKey()).iterator();
                while (itr.hasNext()) {
                    edge_data e = itr.next();
                    g.setColor(Color.GREEN);
                    drawEdge(e, g);
                }
            }
        }
    }

    void drawPokemon(Graphics g) {
        if (_ar != null) {
            List<CL_Pokemon> fs = _ar.getPokemons();
            if (fs != null) {
                Iterator<CL_Pokemon> itr = fs.iterator();

                while (itr.hasNext()) {

                    CL_Pokemon f = itr.next();
                    Point3D c = f.getLocation();

                    int r = 32;
                    //    g.drawImage(this.getPokemon, (int) c.x(), (int) c.y(), null);
                    g.setColor(Color.green);

                    if (f.getType() < 0 && c != null) {
                        //      g.drawImage(this.getPokemon, (int) c.x(), (int) c.y(), null);
                        g.setColor(Color.orange);
                        try {
                            BufferedImage bufferedImage = ImageIO.read(new File("doc/bulbasaur.png"));
                            geo_location fp = this._w2f.world2frame(c);
                            g.drawImage(bufferedImage, (int) fp.x() - r, (int) fp.y() - r, null);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                    if (c != null && f.getType() > 0) {
                        //   geo_location fp = this._w2f.world2frame(c);
                        try {
                            BufferedImage bufferedImage = ImageIO.read(new File("doc/mewtoo.png"));
                            geo_location fp = this._w2f.world2frame(c);
                            g.drawImage(bufferedImage, (int) fp.x() - r, (int) fp.y() - r, null);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }


                    }
                }
            }
        }
    }

    void drawAgents(Graphics g) {


        if (_ar != null) {
            List<CL_Agent> rs = _ar.getAgents();
            //	Iterator<OOP_Point3D> itr = rs.iterator();
            g.setColor(Color.red);
            int i = 0;
            while (rs != null && i < rs.size()) {
                geo_location c = rs.get(i).getLocation();
                int r = 32;
                i++;
                if (c != null) {
                    try {
                        BufferedImage bufferedImage = ImageIO.read(new File("doc/output-onlinepngtools.png"));
                        geo_location fp = this._w2f.world2frame(c);
                        g.drawImage(bufferedImage, (int) fp.x() - r, (int) fp.y() - r, null);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                }
            }
        }
    }

    private void drawNode(node_data n, int r, Graphics g) {
        geo_location pos = n.getLocation();
        geo_location fp = this._w2f.world2frame(pos);
        g.fillOval((int) fp.x() - r, (int) fp.y() - r, 2 * r, 2 * r);
        g.drawString("" + n.getKey(), (int) fp.x(), (int) fp.y() - 4 * r);
    }

    private void drawEdge(edge_data e, Graphics g) {
        directed_weighted_graph gg = _ar.getGraph();
        geo_location s = gg.getNode(e.getSrc()).getLocation();
        geo_location d = gg.getNode(e.getDest()).getLocation();
        geo_location s0 = this._w2f.world2frame(s);
        geo_location d0 = this._w2f.world2frame(d);
        g.drawLine((int) s0.x(), (int) s0.y(), (int) d0.x(), (int) d0.y());
        //	g.drawString(""+n.getKey(), fp.ix(), fp.iy()-4*r);
    }

    private void drawInfo1(Graphics graphics) {


        //   graphics.drawImage(null, x, 40, 40, 0, null);
        if (_ar != null) {
            String str = _ar.getTime();
            String dt = "Time ";
            graphics.setColor(Color.cyan);
            graphics.setFont(new Font("Ariel", Font.BOLD, 24));
            // graphics.drawString(str , 100, 60 + 2 * 20);
            graphics.drawString(str, 0, 105);
            //  graphics.setFont(new Font("Ariel", Font.BOLD, (this.getHeight() + this.getWidth()) / 90));


        }
    }

    private void drawA(Graphics graphics) {


        //   graphics.drawImage(null, x, 40, 40, 0, null);
        if (_ar != null) {

            //ArrayList<OOP_Point3D> rs = new ArrayList<OOP_Point3D>();

            List<CL_Agent> ffs = _ar.getAgents();

            // for (int i = 0; i < _ar.getAgents().size(); i++) {
            if (ffs.size() == 1) {
                CL_Agent ag = ffs.get(0);
                int id = ag.getID();
                int dest = ag.getNextNode();
                double v = ag.getValue();
                String str1 = "Agent: " + id + ", val: " + v + "  turned to node: " + dest + "\n";
                graphics.setColor(Color.CYAN);
                graphics.setFont(new Font("Ariel", Font.BOLD, 25));
                // graphics.drawString(str , 100, 60 + 2 * 20);
                graphics.drawString(str1, 550, 110);

            }
            if (ffs.size() == 2) {
                CL_Agent ag = ffs.get(1);
                int id = ag.getID();
                int dest = ag.getNextNode();
                double v = ag.getValue();
                String str1 = "Agent: " + id + ", val: " + v + "  turned to node: " + dest + "\n";
                graphics.setColor(Color.CYAN);
                graphics.setFont(new Font("Ariel", Font.BOLD, 25));
                // graphics.drawString(str , 100, 60 + 2 * 20);
                graphics.drawString(str1, 550, 115);

                CL_Agent ag1 = ffs.get(0);
                int id1 = ag1.getID();
                int dest1 = ag1.getNextNode();
                double v1 = ag1.getValue();
                String str11 = "Agent: " + id1 + ", val: " + v1 + "  turned to node: " + dest1 + "\n";
                graphics.setColor(Color.GREEN);
                graphics.setFont(new Font("Ariel", Font.BOLD, 25));
                // graphics.drawString(str , 100, 60 + 2 * 20);
                graphics.drawString(str11, 550, 70);


            }
            if (ffs.size() == 3) {
                CL_Agent ag = ffs.get(1);
                int id = ag.getID();
                int dest = ag.getNextNode();
                double v = ag.getValue();
                String str1 = "Agent: " + id + ", val: " + v + "  turned to node: " + dest + "\n";
                graphics.setColor(Color.CYAN);
                graphics.setFont(new Font("Ariel", Font.BOLD, 25));
                // graphics.drawString(str , 100, 60 + 2 * 20);
                graphics.drawString(str1, 550, 105);

                CL_Agent ag1 = ffs.get(0);
                int id1 = ag1.getID();
                int dest1 = ag1.getNextNode();
                double v1 = ag1.getValue();
                String str11 = "Agent: " + id1 + ", val: " + v1 + "  turned to node: " + dest1 + "\n";
                graphics.setColor(Color.MAGENTA);
                graphics.setFont(new Font("Ariel", Font.BOLD, 25));
                // graphics.drawString(str , 100, 60 + 2 * 20);
                graphics.drawString(str11, 550, 65);

                CL_Agent ag2 = ffs.get(2);
                int id2 = ag2.getID();
                int dest2 = ag2.getNextNode();
                double v2 = ag2.getValue();
                String str2 = "Agent: " + id2 + ", val: " + v2 + "  turned to node: " + dest2 + "\n";
                graphics.setColor(Color.GREEN);
                graphics.setFont(new Font("Ariel", Font.BOLD, 25));
                // graphics.drawString(str , 100, 60 + 2 * 20);
                graphics.drawString(str2, 550, 145);


            }
        }
    }

    @Override
    public void mouseClicked(MouseEvent e) {

    }

    @Override
    public void mousePressed(MouseEvent e) {

    }

    @Override
    public void mouseReleased(MouseEvent e) {

    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }
}