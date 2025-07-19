package org.firstinspires.ftc.teamcode.example.subsystem;

import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.rowanmcalpin.nextftc.core.Subsystem;
import com.rowanmcalpin.nextftc.core.command.Command;
import com.rowanmcalpin.nextftc.core.control.controllers.PIDFController;
import com.rowanmcalpin.nextftc.core.control.controllers.feedforward.StaticFeedforward;
import com.rowanmcalpin.nextftc.ftc.hardware.controllables.MotorEx;
import com.rowanmcalpin.nextftc.ftc.hardware.controllables.MotorGroup;
import com.rowanmcalpin.nextftc.ftc.hardware.controllables.RunToPosition;

public class Angle extends Subsystem {
    // BOILERPLATE
    public static final Angle INSTANCE = new Angle();
    private Angle() { }

    // USER CODE
    public MotorEx motorAngleRight;
    public MotorEx motorAngleLeft;
    public PIDFController controller = new PIDFController(10, 0.049988, 0.0, new StaticFeedforward(0.0));
    MotorGroup motorAngleGroup;
    static class AngleTargets {
        public static final int Low = 0;
        public static final int Middle = 250;
        public static final int High = 500;
    }

    public Command toLow() {
        return new RunToPosition(motorAngleGroup, // MOTOR TO MOVE
                AngleTargets.Low, // TARGET POSITION, IN TICKS
                controller, // CONTROLLER TO IMPLEMENT
                this); // IMPLEMENTED SUBSYSTEM
    }

    public Command toMiddle() {
        return new RunToPosition(motorAngleGroup, // MOTOR TO MOVE
                AngleTargets.Middle, // TARGET POSITION, IN TICKS
                controller, // CONTROLLER TO IMPLEMENT
                this); // IMPLEMENTED SUBSYSTEM
    }

    public Command toHigh() {
        return new RunToPosition(motorAngleGroup, // MOTOR TO MOVE
                AngleTargets.High, // TARGET POSITION, IN TICKS
                controller, // CONTROLLER TO IMPLEMENT
                this); // IMPLEMENTED SUBSYSTEM
    }

    public void initialize() {
        motorAngleLeft = new MotorEx("motorAngleLeft");
        motorAngleRight = new MotorEx("motorAngleRight");
         motorAngleGroup = new MotorGroup(motorAngleRight, motorAngleLeft);
        motorAngleLeft.setDirection(DcMotorSimple.Direction.REVERSE);
    }
}
