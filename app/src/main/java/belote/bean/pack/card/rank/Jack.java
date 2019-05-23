/*
 * Copyright (c) Dimitar Karamanov 2008-2014. All Rights Reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the source code must retain
 * the above copyright notice and the following disclaimer.
 *
 * This software is provided "AS IS," without a warranty of any kind.
 */
package belote.bean.pack.card.rank;

/**
 * Rank jack class.
 * @author Dimitar Karamanov
 */
public final class Jack extends Rank {

    private static final long serialVersionUID = -160442047557096206L;
    
	private static final int STANDARD_RANK = 4;

	private static final int ALL_TRUMP_RANK = 7;

	private static final int NOT_TRUMP_RANK = 3;

	private static final int ALL_TRUMP_POINTS = 20;

	private static final int NOT_TRUMP_POINTS = 2;

	private static final int SQUARE_POINTS = 200;

    /**
     * Constructor.
     */
    Jack() {
        super(STANDARD_RANK, ALL_TRUMP_RANK, NOT_TRUMP_RANK, ALL_TRUMP_POINTS, NOT_TRUMP_POINTS, SQUARE_POINTS);
    }
}