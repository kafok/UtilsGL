package com.input;

public enum Joystick 
{
	//desconocido
	UNKNOWN (-1),
	
	//xbox
	A (0),
	B (1),
	X (2),
	Y (3),
	LB (4),
	RB (5),
	BACK (6),
	
	
	//common
	START (7),
	L3 (8),
	R3 (9),
	UP (10),
	RIGHT (11),
	DOWN (12),
	LEFT (13),
	
	
	//play
	CROSS (0),
	CIRCLE (1),
	SQUARE (2),
	TRIANGLE (3),
	L1 (4),
	L2 (5),
	SELECT (6);
	
	
	
	//enum
	private final int id;
	Joystick(int id) { this.id = id; }
    public int getValue() { return id; }
}
