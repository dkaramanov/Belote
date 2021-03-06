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
 * Rank eight class.
 * @author Dimitar Karamanov
 */
public final class Eight extends Rank {

    private static final long serialVersionUID = -4825196775071458888L;
    
	private static final int STANDARD_RANK = 1;

	private static final int ALL_TRUMP_RANK = 1;

	private static final int NOT_TRUMP_RANK = 1;

	private static final int ALL_TRUMP_POINTS = 0;

	private static final int NOT_TRUMP_POINTS = 0;

	private static final int SQUARE_POINTS = 0;

    /**
     * Constructor.
     */
    Eight() {
        super(STANDARD_RANK, ALL_TRUMP_RANK, NOT_TRUMP_RANK, ALL_TRUMP_POINTS, NOT_TRUMP_POINTS, SQUARE_POINTS);
    }
}