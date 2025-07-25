package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.util.ElapsedTime;

@Autonomous(name = "Simple Autonomous", group = "Autonomous")
public class SimpleAutonomous extends LinearOpMode {
    
    // Drive train motors
    private DcMotor leftFrontDrive;
    private DcMotor leftBackDrive;
    private DcMotor rightFrontDrive;
    private DcMotor rightBackDrive;
    
    // Timer for autonomous
    private ElapsedTime runtime = new ElapsedTime();
    
    // Drive constants
    static final double DRIVE_SPEED = 0.6;
    static final double TURN_SPEED = 0.5;
    static final double STRAFE_SPEED = 0.5;
    
    @Override
    public void runOpMode() {
        // Initialize the hardware variables
        leftFrontDrive = hardwareMap.get(DcMotor.class, "leftFrontDrive");
        leftBackDrive = hardwareMap.get(DcMotor.class, "leftBackDrive");
        rightFrontDrive = hardwareMap.get(DcMotor.class, "rightFrontDrive");
        rightBackDrive = hardwareMap.get(DcMotor.class, "rightBackDrive");
        
        // Set motor directions (adjust these based on your robot)
        leftFrontDrive.setDirection(DcMotorSimple.Direction.REVERSE);
        leftBackDrive.setDirection(DcMotorSimple.Direction.REVERSE);
        rightFrontDrive.setDirection(DcMotorSimple.Direction.FORWARD);
        rightBackDrive.setDirection(DcMotorSimple.Direction.FORWARD);
        
        // Set motor modes
        leftFrontDrive.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        leftBackDrive.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        rightFrontDrive.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        rightBackDrive.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        
        // Send telemetry message to signify robot waiting
        telemetry.addData("Status", "Ready to run");
        telemetry.update();
        
        // Wait for the game to start (driver presses START)
        waitForStart();
        
        // Autonomous sequence
        telemetry.addData("Status", "Starting Autonomous");
        telemetry.update();
        
        // Step 1: Drive forward for 2 seconds
        telemetry.addData("Step", "1: Driving Forward");
        telemetry.update();
        driveForward(DRIVE_SPEED, 2.0);
        
        // Step 2: Turn right for 1 second
        telemetry.addData("Step", "2: Turning Right");
        telemetry.update();
        turnRight(TURN_SPEED, 1.0);
        
        // Step 3: Strafe left for 1.5 seconds
        telemetry.addData("Step", "3: Strafing Left");
        telemetry.update();
        strafeLeft(STRAFE_SPEED, 1.5);
        
        // Step 4: Turn left for 1 second
        telemetry.addData("Step", "4: Turning Left");
        telemetry.update();
        turnLeft(TURN_SPEED, 1.0);
        
        // Step 5: Drive backward for 1.5 seconds
        telemetry.addData("Step", "5: Driving Backward");
        telemetry.update();
        driveBackward(DRIVE_SPEED, 1.5);
        
        // Step 6: Stop all motors
        telemetry.addData("Step", "6: Stopping");
        telemetry.update();
        stopMotors();
        
        telemetry.addData("Status", "Autonomous Complete");
        telemetry.update();
    }
    
    // Drive forward for specified time
    private void driveForward(double speed, double time) {
        leftFrontDrive.setPower(speed);
        leftBackDrive.setPower(speed);
        rightFrontDrive.setPower(speed);
        rightBackDrive.setPower(speed);
        
        runtime.reset();
        while (opModeIsActive() && (runtime.seconds() < time)) {
            telemetry.addData("Path", "Forward: %4.1f S Elapsed", runtime.seconds());
            telemetry.update();
        }
    }
    
    // Drive backward for specified time
    private void driveBackward(double speed, double time) {
        leftFrontDrive.setPower(-speed);
        leftBackDrive.setPower(-speed);
        rightFrontDrive.setPower(-speed);
        rightBackDrive.setPower(-speed);
        
        runtime.reset();
        while (opModeIsActive() && (runtime.seconds() < time)) {
            telemetry.addData("Path", "Backward: %4.1f S Elapsed", runtime.seconds());
            telemetry.update();
        }
    }
    
    // Turn right for specified time
    private void turnRight(double speed, double time) {
        leftFrontDrive.setPower(speed);
        leftBackDrive.setPower(speed);
        rightFrontDrive.setPower(-speed);
        rightBackDrive.setPower(-speed);
        
        runtime.reset();
        while (opModeIsActive() && (runtime.seconds() < time)) {
            telemetry.addData("Path", "Turn Right: %4.1f S Elapsed", runtime.seconds());
            telemetry.update();
        }
    }
    
    // Turn left for specified time
    private void turnLeft(double speed, double time) {
        leftFrontDrive.setPower(-speed);
        leftBackDrive.setPower(-speed);
        rightFrontDrive.setPower(speed);
        rightBackDrive.setPower(speed);
        
        runtime.reset();
        while (opModeIsActive() && (runtime.seconds() < time)) {
            telemetry.addData("Path", "Turn Left: %4.1f S Elapsed", runtime.seconds());
            telemetry.update();
        }
    }
    
    // Strafe left for specified time (mecanum drive)
    private void strafeLeft(double speed, double time) {
        leftFrontDrive.setPower(-speed);
        leftBackDrive.setPower(speed);
        rightFrontDrive.setPower(speed);
        rightBackDrive.setPower(-speed);
        
        runtime.reset();
        while (opModeIsActive() && (runtime.seconds() < time)) {
            telemetry.addData("Path", "Strafe Left: %4.1f S Elapsed", runtime.seconds());
            telemetry.update();
        }
    }
    
    // Strafe right for specified time (mecanum drive)
    private void strafeRight(double speed, double time) {
        leftFrontDrive.setPower(speed);
        leftBackDrive.setPower(-speed);
        rightFrontDrive.setPower(-speed);
        rightBackDrive.setPower(speed);
        
        runtime.reset();
        while (opModeIsActive() && (runtime.seconds() < time)) {
            telemetry.addData("Path", "Strafe Right: %4.1f S Elapsed", runtime.seconds());
            telemetry.update();
        }
    }
    
    // Stop all motors
    private void stopMotors() {
        leftFrontDrive.setPower(0);
        leftBackDrive.setPower(0);
        rightFrontDrive.setPower(0);
        rightBackDrive.setPower(0);
    }
} 