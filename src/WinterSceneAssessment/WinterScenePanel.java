package WinterSceneAssessment;

// (c) A+ Computer Science
// www.apluscompsci.com
// Name -
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Canvas;
import java.awt.Font;
import javax.swing.JPanel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyListener;
import java.awt.event.KeyEvent;
import java.util.List;
import java.util.ArrayList;

public class WinterScenePanel extends JPanel implements KeyListener, Runnable {

    private List<AbstractShape> shapes;
    private AbstractShape sMan;
    private boolean storm;

    public WinterScenePanel() {
        setVisible(true);
        shapes = new ArrayList<AbstractShape>();
        for (int i = 0; i < 50; i++) {
            int y = (int) (Math.random() * 600);
            int s = (int) (Math.random() * 30) + 20;
            shapes.add(new SimpleSnowFlake(i * 14, y, s, s));
            shapes.add(new FancySnowFlake(i * 14, y, s, s));
        }
        sMan = new SnowMan(500, 350, 200, 150);
        storm = false;
        addKeyListener(this);
        new Thread(this).start();
    }

    public void update(Graphics window) {

        paint(window);
    }

    public void paint(Graphics window) {
        window.setColor(Color.BLUE);
        window.fillRect(0, 0, getWidth(), getHeight());
        window.setColor(Color.WHITE);
        window.drawRect(20, 20, getWidth() - 40, getHeight() - 40);
        window.setFont(new Font("TAHOMA", Font.BOLD, 18));
        window.drawString("MAKE A WINTER SCENE!", 40, 40);

        if (storm) {
            for (int i = 0; i < shapes.size(); i++) {
                if (shapes.get(i) instanceof SimpleSnowFlake) {
                    shapes.add(new StormySnowFlake(shapes.get(i).getXPos(), shapes.get(i).getYPos(), shapes.get(i).getWidth(), shapes.get(i).getHeight(), shapes.get(i).getColor(), "simple"));
                    shapes.remove(i--);
                } else if (shapes.get(i) instanceof FancySnowFlake) {
                    shapes.add(new StormySnowFlake(shapes.get(i).getXPos(), shapes.get(i).getYPos(), shapes.get(i).getWidth(), shapes.get(i).getHeight(), shapes.get(i).getColor(), "fancy"));
                    shapes.remove(i--);
                }

            }
        }
        sMan.draw(window);
        for (AbstractShape sh : shapes) {
            sh.moveAndDraw(window);
            if (sh.getYPos() >= getHeight()) {
                sh.setYPos(0);
            }
            if (sh.getXPos() > getWidth()) {
                sh.setXPos(0);
            }
            if (sh.getXPos() < 0) {
                sh.setXPos(getWidth());
            }
        }
    }

    public void run() {
        try {
            while (true) {
                Thread.currentThread().sleep(35);
                repaint();
            }
        } catch (Exception e) {
        }
    }

    @Override
    public void keyTyped(KeyEvent e) {
        //do nothing
    }

    @Override
    public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_W) {
            storm = true;
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        //do nothing
    }
}
