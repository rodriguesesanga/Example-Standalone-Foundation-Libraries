/*
 * Copyright 2016-2020 MicroEJ Corp. All rights reserved.
 * Use of this source code is governed by a BSD-style license that can be found with this software.
 */
package com.microej.example.foundation.microui.input;

import ej.microui.event.Event;
import ej.microui.event.EventGenerator;
import ej.microui.event.generator.Buttons;
import ej.microui.event.generator.Command;
import ej.microui.event.generator.Pointer;
import ej.microui.event.generator.States;

/**
 *
 */
public class EventHandlerImpl implements ej.microui.event.EventHandler {

	private final long startTime = System.currentTimeMillis();;


	/**
	 * Describes the Pointer event: print the pointer's new position
	 *
	 * @param gen
	 *            the Pointer generator
	 * @param data
	 *            the Pointer data
	 */
	private void showPointer(EventGenerator gen, int data) {
		System.out.print("position\t");

		if (gen != null) {

			Pointer pointer = (Pointer) gen;

			// get the new position
			int x = pointer.getX();
			int y = pointer.getY();
			System.out.println("(" + x + "," + y + ")");
			// may be it is a button event
			showButton(data);
		} else {
			// bad event: it is a Pointer event but there is no
			// associated EventGenerator: we can't get the position
			System.out.println("unknown");
		}
	}

	/**
	 * Describes the Button event: print the button's id & state.
	 *
	 * @param data
	 *            the Button data
	 */
	private void showButton(int data) {

		String state = null;

		// print its state(s)
		if (Buttons.isPressed(data)) {
			state = "pressed ";
		}
		if (Buttons.isReleased(data)) {
			state = "released ";
		}
		if (Buttons.isRepeated(data)) {
			state = "repeated ";
		}
		if (Buttons.isClicked(data)) {
			state = "clicked ";
		}
		if (Buttons.isDoubleClicked(data)) {
			state = "double-clicked ";
		}

		if (state != null) {
			System.out.print("button\t\t");

			// get the button's id
			int id = Buttons.getButtonId(data);
			System.out.print(id + " ");
			System.out.println(state);
		}
	}

	/**
	 * Describes the Command event: print the command's type
	 *
	 * @param data
	 *            the Command data
	 */
	private void showCommand(int data) {
		System.out.print("command\t\t");

		// print the right descriptor
		switch (data) {
		case Command.ESC: System.out.println("ESC"); break;
		case Command.BACK: System.out.println("BACK"); break;
		case Command.UP: System.out.println("UP"); break;
		case Command.DOWN: System.out.println("DOWN"); break;
		case Command.LEFT: System.out.println("LEFT"); break;
		case Command.RIGHT: System.out.println("RIGHT"); break;
		case Command.SELECT: System.out.println("SELECT"); break;
		case Command.CANCEL: System.out.println("CANCEL"); break;
		case Command.HELP: System.out.println("HELP"); break;
		case Command.MENU: System.out.println("MENU"); break;
		case Command.EXIT: System.out.println("EXIT"); break;
		case Command.START: System.out.println("START"); break;
		case Command.STOP: System.out.println("STOP"); break;
		case Command.PAUSE: System.out.println("PAUSE"); break;
		case Command.RESUME: System.out.println("RESUME"); break;
		case Command.COPY: System.out.println("COPY"); break;
		case Command.CUT: System.out.println("CUT"); break;
		case Command.PASTE: System.out.println("PASTE"); break;
		case Command.CLOCKWISE: System.out.println("CLOCKWISE"); break;
		case Command.ANTICLOCKWISE: System.out.println("ANTICLOCKWISE"); break;
		case Command.PREVIOUS: System.out.println("PREVIOUS"); break;
		case Command.NEXT: System.out.println("NEXT"); break;
		default:
			// it is not a standard MicroUI Command
			System.out.println("unknown (0x" + Integer.toHexString(data) + ")");
			break;
		}
	}

	/**
	 * Describes the State event: print the state's id & value.
	 *
	 * @param gen
	 *            the States generator
	 * @param data
	 *            the State data
	 */
	private void showState(int data) {
		int stateId = States.getStateId(data);
		int value = States.getStateValue(data);
		System.out.println("state\t\t" + stateId + " value: " + value);
	}

	/**
	 * Called by the MicroUI framework when an input event occurs. This method
	 * describes the input event.
	 */
	@Override
	public boolean handleEvent(int event) {
		System.out.println("Event\t\t0x" + Integer.toHexString(event) + " after "
				+ (System.currentTimeMillis() - startTime) + "ms");

		// get the generator's id from the event
		int genId = Event.getGeneratorId(event);
		System.out.print("EventGenerator\t" + genId + " (");

		EventGenerator gen;
		try {
			// get the EventGenerator from the event & print its type
			gen = EventGenerator.get(genId);
			System.out.println(gen.getClass().getName() + ")");
		} catch (IndexOutOfBoundsException e) {
			// EventGenerator.get(genId) throws an
			// ArrayIndexOutOfBoundsException
			// if the generator id got from the event is invalid
			gen = null;
			System.out.println("unknown)");
		}

		// get the event's type & data
		int type = Event.getType(event);
		int data = Event.getData(event);

		// according to the event's type, describe the data
		switch (type) {
		case Command.EVENT_TYPE:
			showCommand(data);
			break;
		case Buttons.EVENT_TYPE:
			showButton(data);
			break;
		case Pointer.EVENT_TYPE:
			showPointer(gen, data);
			break;
		case States.EVENT_TYPE:
			showState(data);
			break;

		default:
			// unknown event
			System.out.println("Unknown event: 0x" + Integer.toHexString(event));
			break;
		}

		System.out.println();
		return true;
	}

}
