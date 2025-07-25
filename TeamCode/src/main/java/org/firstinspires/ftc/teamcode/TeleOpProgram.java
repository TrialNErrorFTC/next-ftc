package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.util.Range;
import com.rowanmcalpin.nextftc.ftc.NextFTCOpMode;

import org.firstinspires.ftc.teamcode.example.subsystem.Extension;

@TeleOp(name = "NextFTC TeleOp Program", group = "TeleOp")
public class TeleOpProgram extends NextFTCOpMode {
    
    // Drive train motors
    private DcMotor leftFrontDrive;
    private DcMotor leftBackDrive;
    private DcMotor rightFrontDrive;
    private DcMotor rightBackDrive;
    
    // Extension subsystem
    private Extension extension;
    
    // Drive mode variables
    private boolean isTankDrive = false; // Toggle between tank and mecanum drive
    private double driveSpeed = 1.0; // Full speed by default
    
    public TeleOpProgram() {
        super(Extension.INSTANCE);
    }
    
    @Override
    public void initialize() {
        // Initialize drive train motors
        leftFrontDrive = hardwareMap.get(DcMotor.class, "leftFrontDrive");
        leftBackDrive = hardwareMap.get(DcMotor.class, "leftBackDrive");
        rightFrontDrive = hardwareMap.get(DcMotor.class, "rightFrontDrive");
        rightBackDrive = hardwareMap.get(DcMotor.class, "rightBackDrive");
        
        // Set motor directions (you may need to adjust these based on your robot)
        leftFrontDrive.setDirection(DcMotorSimple.Direction.REVERSE);
        leftBackDrive.setDirection(DcMotorSimple.Direction.REVERSE);
        rightFrontDrive.setDirection(DcMotorSimple.Direction.FORWARD);
        rightBackDrive.setDirection(DcMotorSimple.Direction.FORWARD);
        
        // Set motor modes
        leftFrontDrive.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        leftBackDrive.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        rightFrontDrive.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        rightBackDrive.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        
        // Initialize extension subsystem
        extension = Extension.INSTANCE;
        
        telemetry.addData("Status", "TeleOp Initialized");
        telemetry.addData("Drive Mode", isTankDrive ? "Tank" : "Mecanum");
        telemetry.addData("Speed", "%.1f", driveSpeed);
        telemetry.update();
    }
    
    @Override
    public void loop() {
        // Handle drive mode toggle
        if (gamepad1.y && !gamepad1.y) { // Y button toggles drive mode
            isTankDrive = !isTankDrive;
            telemetry.addData("Drive Mode Changed", isTankDrive ? "Tank" : "Mecanum");
        }
        
        // Handle speed adjustment
        if (gamepad1.right_bumper) {
            driveSpeed = 0.5; // Slow speed
        } else if (gamepad1.left_bumper) {
            driveSpeed = 0.25; // Very slow speed
        } else {
            driveSpeed = 1.0; // Full speed
        }
        
        // Drive the robot
        if (isTankDrive) {
            tankDrive();
        } else {
            mecanumDrive();
        }
        
        // Handle extension controls
        handleExtension();
        
        // Update telemetry
        updateTelemetry();
    }
    
    private void tankDrive() {
        // Tank drive: left stick controls left side, right stick controls right side
        double leftPower = -gamepad1.left_stick_y * driveSpeed;
        double rightPower = -gamepad1.right_stick_y * driveSpeed;
        
        // Apply power to motors
        leftFrontDrive.setPower(leftPower);
        leftBackDrive.setPower(leftPower);
        rightFrontDrive.setPower(rightPower);
        rightBackDrive.setPower(rightPower);
    }
    
    private void mecanumDrive() {
        // Mecanum drive: left stick for forward/backward and strafe, right stick for rotation
        double axial = -gamepad1.left_stick_y * driveSpeed;   // Forward/backward
        double lateral = gamepad1.left_stick_x * driveSpeed;  // Strafe left/right
        double yaw = gamepad1.right_stick_x * driveSpeed;     // Rotation
        
        // Calculate motor powers for mecanum drive
        double leftFrontPower = axial + lateral + yaw;
        double rightFrontPower = axial - lateral - yaw;
        double leftBackPower = axial - lateral + yaw;
        double rightBackPower = axial + lateral - yaw;
        
        // Normalize powers to prevent exceeding 1.0
        double max = Math.max(Math.abs(leftFrontPower), Math.abs(rightFrontPower));
        max = Math.max(max, Math.abs(leftBackPower));
        max = Math.max(max, Math.abs(rightBackPower));
        
        if (max > 1.0) {
            leftFrontPower /= max;
            rightFrontPower /= max;
            leftBackPower /= max;
            rightBackPower /= max;
        }
        
        // Apply power to motors
        leftFrontDrive.setPower(leftFrontPower);
        leftBackDrive.setPower(leftBackPower);
        rightFrontDrive.setPower(rightFrontPower);
        rightBackDrive.setPower(rightBackPower);
    }
    
    private void handleExtension() {
        // Extension controls using gamepad2
        if (gamepad2.a) {
            // Move extension to low position
            schedule(extension.toLow());
        } else if (gamepad2.b) {
            // Move extension to middle position
            schedule(extension.toMiddle());
        } else if (gamepad2.x) {
            // Move extension to high position
            schedule(extension.toHigh());
        } else if (gamepad2.y) {
            // Hold current position
            schedule(extension.holdPosition());
        }
        
        // Manual extension control with triggers
        if (gamepad2.right_trigger > 0.1) {
            // Manual up
            extension.motorExtensionGroup.setPower(gamepad2.right_trigger * 0.5);
        } else if (gamepad2.left_trigger > 0.1) {
            // Manual down
            extension.motorExtensionGroup.setPower(-gamepad2.left_trigger * 0.5);
        } else {
            // Stop manual movement
            extension.motorExtensionGroup.setPower(0);
        }
    }
    
    private void updateTelemetry() {
        telemetry.addData("Drive Mode", isTankDrive ? "Tank" : "Mecanum");
        telemetry.addData("Speed", "%.1f", driveSpeed);
        telemetry.addData("Left Front Power", "%.2f", leftFrontDrive.getPower());
        telemetry.addData("Left Back Power", "%.2f", leftBackDrive.getPower());
        telemetry.addData("Right Front Power", "%.2f", rightFrontDrive.getPower());
        telemetry.addData("Right Back Power", "%.2f", rightBackDrive.getPower());
        telemetry.addData("Extension Position", "%.0f", extension.motorExtensionGroup.getCurrentPosition());
        telemetry.addData("Controls", "Y: Toggle Drive | RB/LB: Speed | A/B/X/Y: Extension | RT/LT: Manual");
        telemetry.update();
    }
}
