package org.firstinspires.ftc.teamcode.example.subsystem;

import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.rowanmcalpin.nextftc.core.Subsystem;
import com.rowanmcalpin.nextftc.core.command.Command;
import com.rowanmcalpin.nextftc.core.command.utility.InstantCommand;
import com.rowanmcalpin.nextftc.core.control.controllers.PIDFController;
import com.rowanmcalpin.nextftc.core.control.controllers.feedforward.StaticFeedforward;
import com.rowanmcalpin.nextftc.ftc.hardware.controllables.HoldPosition;
import com.rowanmcalpin.nextftc.ftc.hardware.controllables.MotorEx;
import com.rowanmcalpin.nextftc.ftc.hardware.controllables.MotorGroup;
import com.rowanmcalpin.nextftc.ftc.hardware.controllables.RunToPosition;


public class Extension extends Subsystem {
    // BOILERPLATE
    public static final Extension INSTANCE = new Extension();
    private Extension() { }

    // USER CODE
    public MotorEx motorExtensionRight;
    public MotorEx motorExtensionLeft;
    public MotorGroup motorExtensionGroup;
    public PIDFController controller = new PIDFController(10, 0.049988, 0.0, new StaticFeedforward(0.0));

    public Command resetZero() {
        return new InstantCommand(() -> { motorExtensionRight.resetEncoder(); });
    }
    
    public Command toLow() {
        return new RunToPosition(motorExtensionGroup, // MOTOR TO MOVE
                0.0, // TARGET POSITION, IN TICKS
                controller, // CONTROLLER TO IMPLEMENT
                this); // IMPLEMENTED SUBSYSTEM
    }

    public Command toMiddle() {
        return new RunToPosition(motorExtensionGroup, // MOTOR TO MOVE
                500.0, // TARGET POSITION, IN TICKS
                controller, // CONTROLLER TO IMPLEMENT
                this); // IMPLEMENTED SUBSYSTEM
    }

    public Command toHigh() {
        return new RunToPosition(motorExtensionGroup, // MOTOR TO MOVE
                1200.0, // TARGET POSITION, IN TICKS
                controller, // CONTROLLER TO IMPLEMENT
                this); // IMPLEMENTED SUBSYSTEM
    }
    public Command holdPosition(){
        return new HoldPosition(
                motorExtensionGroup,
                controller,
                this
        );
    }
    
    @Override
    public void initialize() {
        motorExtensionLeft = new MotorEx("motorExtensionLeft");
        motorExtensionRight = new MotorEx("motorExtensionRight");
        motorExtensionGroup = new MotorGroup(motorExtensionRight, motorExtensionLeft);
        motorExtensionLeft.setDirection(DcMotorSimple.Direction.REVERSE);
    }
}
