package com.quiltplayer.core;

import java.util.ArrayList;

import org.apache.log4j.Logger;

/**
 * List that keeps track of duplicates. When adding an entry that already
 * exists, the old entry will get deleted and the new one will be added on the
 * old entry's index.
 * 
 * @author Vlado Palczynski
 * @param <T>
 */
public class QList<T> extends ArrayList<T>
{
	/**
     *
     */
	private static final long serialVersionUID = 1L;

	private static Logger log = Logger.getLogger(QList.class);

	/**
	 * @param t
	 *            the t to set.
	 * @return boolean.
	 */
	@Override
	public final boolean add(final T t)
	{
		if (this.contains(t))
		{
			log.debug("Entry already exists in list, overwriting...");

			int i = indexOf(t);
			remove(i);
			add(i, t);

			return true;
		}
		else
		{
			return super.add(t);
		}
	}

}
