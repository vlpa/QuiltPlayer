package com.quiltplayer.properties;

import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;

/**
 * Image properties.
 * 
 * @author Vlado Palczynski
 */
public class ImageProperties
{
	/**
	 * The playlist panel should have a default picture then entering the
	 * application.
	 */
	public static final Resource LOGO_PATH = new ClassPathResource("logo.gif");

	/**
	 * Path to splash screen.
	 */
	public static final String SPLASH = "images/splash.jpg";

}
