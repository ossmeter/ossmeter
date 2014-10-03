package sparky;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import javax.imageio.ImageIO;

public class Sparky {
	
	protected final int imgHeight;
	protected final int imgWidth;
	protected final int padding;
	
	public Sparky(int imgHeight, int imgWidth, int padding) {
		this.imgHeight = imgHeight;
		this.imgWidth= imgWidth;
		this.padding = padding;
	}

	public byte[] render(Object data) throws IOException {
		
		BufferedImage im = new BufferedImage(imgWidth, imgHeight, BufferedImage.TYPE_INT_RGB);
		
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		ImageIO.write(im, "png", baos);
		baos.flush();
		byte[] imageInByte = baos.toByteArray();
		baos.close();
		
		return imageInByte;
	}
	
	
	public static void main(String[] args) throws Exception{
		
		LinearScale s = new LinearScale(10.0, 0.0, 5.0, 0.0);
		System.out.println(s.scale(5d));
		
		DateScale s2 = new DateScale(new Date(2014, 0, 30), new Date(2014, 0, 0), 10, 0);
		System.out.println(s2.scale(new Date(2014, 0, 15)));
		
		int height = 30;
		int width = 150;
		int padding = 6;
		
		// Save
		BufferedImage im = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
		Graphics2D g = (Graphics2D) im.getGraphics();
		
		RenderingHints rh = new RenderingHints(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
		rh.put(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
	    g.setRenderingHints(rh);
		
	    List<Integer> xdata = Arrays.asList(0,1,2,3,4,5,6,7,8,9,10,11,12);
	    List<Integer> ydata = Arrays.asList(3,2,6,14,7,23,7,8,12,6,8,2,4);
	    
	    Integer maxX = Collections.max(xdata);
		Integer minX = Collections.min(xdata);
		
	    Integer maxY = Collections.max(ydata);
		Integer minY = Collections.min(ydata);

		LinearScale xScale = new LinearScale(maxX, minX, width-padding, padding);
		LinearScale yScale = new LinearScale(maxY, minY, height-padding, padding);
	    
	    g.setColor(Color.white);
	    g.fillRect(0, 0, width, height);

	    Color c = new Color(0, 115, 180);
	    g.setColor(c);
	    BasicStroke stroke = new BasicStroke(2);
	    g.setStroke(stroke);
	    
	    // Draw lines
	    
	    int prevX = xScale.scale((double)xdata.get(0));
	    int prevY = yScale.scale((double)ydata.get(0));
	    
	    for (int i = 1; i< xdata.size(); i++) {
	    	
	    	System.out.println(prevX + ", " + prevY);
	    	
	    	int x = xScale.scale((double)xdata.get(i));
	    	int y = yScale.scale((double)ydata.get(i));
	    	
	    	g.drawLine(prevX, prevY, x, y);
	    	prevX = x;
	    	prevY = y;
	    }
	    
	    // Draw high and low
	    Color orange = new Color(255, 148, 0);
	    g.setColor(orange);
	    
	    
	    int x1 = xdata.get(ydata.indexOf(minY));
	    int x2 = xdata.get(ydata.indexOf(maxY));
	    
	    g.drawOval(xScale.scale((double)x1)-1, yScale.scale((double)minY)-1, 2, 2);
	    g.drawOval(xScale.scale((double)x2)-1, yScale.scale((double)maxY)-1, 2, 2);
		
		ImageIO.write(im, "png", new File("test.png"));
		g.dispose();
	}
}
