/*
 * Copyright (c) Dimitar Karamanov 2008-2014. All Rights Reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the source code must retain
 * the above copyright notice and the following disclaimer.
 *
 * This software is provided "AS IS," without a warranty of any kind.
 */
package belote.bean.pack.card.suit;

/**
 * Suit diamond class.
 * @author Dimitar Karamanov
 */
public final class Diamond extends Suit {

    /**
	 * SerialVersionUID
	 */
    private static final long serialVersionUID = -1654169925121692794L;
    
    /**
     * Diamond ID constant.
     */
    private static final int ID = 1;

    /**
     * Constructor.
     */
    Diamond() {
        super(ID);
    }

    /**
     * Returns suit's color.
     * @return String suit's color.
     */
    public String getSuitColor() {
        return "red";
    }
}