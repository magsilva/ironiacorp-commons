package com.ironiacorp.imaging.png.encoder;

import java.awt.image.BufferedImage;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Iterator;

import javax.imageio.IIOImage;
import javax.imageio.ImageIO;
import javax.imageio.ImageTypeSpecifier;
import javax.imageio.ImageWriteParam;
import javax.imageio.ImageWriter;


/**
 * PNG encoder (http://catcode.com/pngencoder/)
 */
public class JclPngEncoder implements PngImageEncoder
{
	private boolean alphaChannel;

	private int compressionLevel;

	public int getCompressionLevel()
	{
		return compressionLevel;
	}

	public void setCompressionLevel(int compressionLevel)
	{
		this.compressionLevel = compressionLevel;
	}

	public boolean useAlphaChannel()
	{
		return alphaChannel;
	}

	public void setAlphaChannel(boolean alphaChannel)
	{
		this.alphaChannel = alphaChannel;
	}

	@Override
	public byte[] encode(BufferedImage image)
	{
		Iterator<ImageWriter> writers = ImageIO.getImageWritersByFormatName("png");
		ImageWriter imageWriter = writers.next();
		ImageWriteParam params = imageWriter.getDefaultWriteParam();
			
		params.setCompressionMode(ImageWriteParam.MODE_EXPLICIT);
		params.setCompressionQuality(0);
		params.setCompressionType("DEFAULT");
		params.setProgressiveMode(ImageWriteParam.MODE_COPY_FROM_METADATA);
		params.setDestinationType(new ImageTypeSpecifier(image.getColorModel(), image.getSampleModel()));
		
				
		ByteArrayOutputStream os = new ByteArrayOutputStream();
		byte[] result = null;
		try {
			imageWriter.setOutput(os);
			imageWriter.write(null, new IIOImage(image, null, null ), params);	
			result = os.toByteArray();
			os.close();
		} catch (IOException e) {
			throw new IllegalArgumentException(e);
		}

		return result;
	}

	/**
	 * Takes a BufferedImage object, and if the color model is DirectColorModel, converts it to be a
	 * ComponentColorModel suitable for fast PNG writing. If the color model is any other color
	 * model than DirectColorModel, a reference to the original image is simply returned.
	 * (http://forums.sun.com/thread.jspa?messageID=9889549#9889549)
	 * 
	 * @param source The source image.
	 * @return The converted image.
	 */
	/*
	private BufferedImage convertColorModelPNG(BufferedImage source)
	{
		if (!(source.getColorModel() instanceof DirectColorModel)) {
			return source;
		}
		ICC_Profile newProfile = ICC_Profile.getInstance(ColorSpace.CS_sRGB);
		ICC_ColorSpace newSpace = new ICC_ColorSpace(newProfile);
		ComponentColorModel newModel = new ComponentColorModel(newSpace, true, false,
						ComponentColorModel.TRANSLUCENT, DataBuffer.TYPE_BYTE);

		PixelInterleavedSampleModel newSampleModel = new PixelInterleavedSampleModel(
						DataBuffer.TYPE_BYTE, source.getWidth(), source.getHeight(), 4, source
										.getWidth() * 4, new int[] { 0, 1, 2, 3 });
		DataBufferByte newDataBuffer = new DataBufferByte(source.getWidth() * source.getHeight()
						* 4);
		ByteInterleavedRaster newRaster = new ByteInterleavedRaster(newSampleModel, newDataBuffer,
						new Point(0, 0));

		BufferedImage dest = new BufferedImage(newModel, newRaster, false, new Hashtable());

		int[] srcData = ((DataBufferInt) source.getRaster().getDataBuffer()).getData();
		byte[] destData = newDataBuffer.getData();
		int j = 0;
		byte argb = 0;
		for (int i = 0; i < srcData.length; i++) {
			j = i * 4;
			argb = (byte) (srcData[i] >> 24);
			destData[j] = argb;
			destData[j + 1] = 0;
			destData[j + 2] = 0;
			destData[j + 3] = 0;
		}

		return dest;
	}
	*/

}
