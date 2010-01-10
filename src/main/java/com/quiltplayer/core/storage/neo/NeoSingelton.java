/**
 * QuiltPlayer v1.0 Copyright (C) 2008-2009 Vlado Palczynski
 * vlado.palczynski@quiltplayer.com http://www.quiltplayer.com This program is
 * free software; you can redistribute it and/or modify it under the terms of
 * the GNU General Public License as published by the Free Software Foundation;
 * either version 2 of the License, or (at your option) any later version. This
 * program is distributed in the hope that it will be useful, but WITHOUT ANY
 * WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR
 * A PARTICULAR PURPOSE. See the GNU General Public License for more details.
 */
package com.quiltplayer.core.storage.neo;

import org.neo4j.api.core.EmbeddedNeo;
import org.neo4j.api.core.NeoService;
import org.neo4j.util.NeoUtil;

import com.quiltplayer.properties.Configuration;

/**
 * @author Vlado Palczynski
 */
public class NeoSingelton {
	private static final String NEO_FOLDER = "/neo";

	private NeoService neo;

	private NeoUtil neoUtil;

	private static NeoSingelton instance;

	private NeoSingelton() {
		String neoFolder = Configuration.getRoot() + NEO_FOLDER;
		neo = new EmbeddedNeo(neoFolder);
		neoUtil = new NeoUtil(neo);
	}

	public static NeoSingelton getInstance() {
		if (instance == null)
			instance = new NeoSingelton();

		return instance;
	}

	public NeoService getNeoService() {
		return neo;
	}

	public NeoUtil getNeoUtil() {
		return neoUtil;
	}
}
