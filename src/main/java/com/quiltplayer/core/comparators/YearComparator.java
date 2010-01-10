package com.quiltplayer.core.comparators;

import java.io.Serializable;
import java.util.Comparator;

import org.apache.commons.lang.StringUtils;

import com.quiltplayer.model.Album;

/**
 * Compare by year.
 * 
 * @author Vlado Palczynski
 */
public class YearComparator implements Serializable, Comparator<Album>
{
    /**
     * 
     */
    private static final long serialVersionUID = -1132934238602099243L;

    /*
     * (non-Javadoc)
     * @see java.util.Comparator#compare(java.lang.Object, java.lang.Object)
     */
    @Override
    public int compare(final Album a1, final Album a2)
    {
        if (a1 == null && a2 == null)
        {
            return 0;
        }
        else if (a1 == null)
        {
            return -1;
        }
        else if (a2 == null)
        {
            return 1;
        }
        else if (StringUtils.isBlank(a1.getYear())
                && StringUtils.isBlank(a2.getYear()))
        {
            return 0;
        }
        else if (StringUtils.isBlank(a1.getYear()))
        {
            return 1;
        }
        else if (StringUtils.isBlank(a2.getYear()))
        {
            return -1;
        }

        return a2.getYear().compareTo(a1.getYear());
    }
}
