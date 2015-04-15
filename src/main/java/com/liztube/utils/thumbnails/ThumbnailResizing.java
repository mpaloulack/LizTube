package com.liztube.utils.thumbnails;

import java.awt.*;
import java.awt.image.BufferedImage;

/**
 * Image resizing class
 */
public class ThumbnailResizing {

    private static final double WIDTH_HEIGHT_RAPPORT = 1.78;// 16/9
    private static final double HEIGHT_WIDTH_RAPPORT = 0.56;// 9/16
    private static final int DEFAULT_THUMBNAIL_WIDTH = 320;// 16
    private static final int DEFAULT_THUMBNAIL_HEIGHT = 180;// 9

    /**
     * Adapt thumbnail size :
     * 1) Crop
     * 2) Resize to default thumbnail size
     * @param originalImage
     * @return
     */
    public static BufferedImage adaptThumbnailSize(BufferedImage originalImage){
        return resizeImageWithHint(cropThumbnail(originalImage));
    }

    /**
     * Crop image according to defined rapport
     * @param originalImage
     * @return
     */
    private static BufferedImage cropThumbnail(BufferedImage originalImage){
        int originalWidth = originalImage.getWidth();
        int originalHeight = originalImage.getHeight();

        long computedWidth = Math.round(originalHeight * WIDTH_HEIGHT_RAPPORT);
        if(computedWidth > originalWidth){
            long computedHeight = Math.round(originalWidth * HEIGHT_WIDTH_RAPPORT);
            long heightDecal = (originalHeight - computedHeight) / 2;
            return originalImage.getSubimage(0, (int)heightDecal, originalWidth, (int)computedHeight);
        }else{
            long widthDecal = (originalWidth - computedWidth) / 2;
            return originalImage.getSubimage((int)widthDecal, 0, (int)computedWidth, originalHeight);
        }
    }

    /**
     * Resize image with increasing quality
     * @param originalImage
     * @return
     */
    private static BufferedImage resizeImageWithHint(BufferedImage originalImage){
        int type = originalImage.getType() == 0? BufferedImage.TYPE_INT_ARGB : originalImage.getType();
        BufferedImage resizedImage = new BufferedImage(DEFAULT_THUMBNAIL_WIDTH, DEFAULT_THUMBNAIL_HEIGHT, type);
        Graphics2D g = resizedImage.createGraphics();
        g.drawImage(originalImage, 0, 0, DEFAULT_THUMBNAIL_WIDTH, DEFAULT_THUMBNAIL_HEIGHT, null);
        g.dispose();
        g.setComposite(AlphaComposite.Src);

        g.setRenderingHint(RenderingHints.KEY_INTERPOLATION,
                RenderingHints.VALUE_INTERPOLATION_BILINEAR);
        g.setRenderingHint(RenderingHints.KEY_RENDERING,
                RenderingHints.VALUE_RENDER_QUALITY);
        g.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                RenderingHints.VALUE_ANTIALIAS_ON);

        return resizedImage;
    }

    /**
     * Resize image
     * @param originalImage
     * @return
     */
    /*
    private static BufferedImage resizeImage(BufferedImage originalImage){
        int type = originalImage.getType() == 0? BufferedImage.TYPE_INT_ARGB : originalImage.getType();
        BufferedImage resizedImage = new BufferedImage(DEFAULT_THUMBNAIL_WIDTH, DEFAULT_THUMBNAIL_HEIGHT, type);
        Graphics2D g = resizedImage.createGraphics();
        g.drawImage(originalImage, 0, 0, DEFAULT_THUMBNAIL_WIDTH, DEFAULT_THUMBNAIL_HEIGHT, null);
        g.dispose();

        return resizedImage;
    }*/

}
