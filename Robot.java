package frc.robot;

import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
// added imports below
import edu.wpi.first.wpilibj.Servo;
import com.revrobotics.CANSparkLowLevel.MotorType;
import com.revrobotics.CANSparkMax;
//import edu.wpi.first.cameraserver.CameraServer;
import edu.wpi.first.math.MathUtil;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.PneumaticsModuleType;
import edu.wpi.first.wpilibj.DoubleSolenoid.Value;
// import java.lang.Math;


public class Robot extends TimedRobot {
  private static final String kDefaultAuto = "Default";
  private static final String kCustomAuto = "My Auto";
  private static final String kopenside = "open side";
  private String m_autoSelected;
  private final SendableChooser<String> m_chooser = new SendableChooser<>();
  // drive motors
  CANSparkMax frontRight = new CANSparkMax(1, MotorType.kBrushless); //frontright
  CANSparkMax backRight = new CANSparkMax(2, MotorType.kBrushless);  //backright
  CANSparkMax frontLeft = new CANSparkMax(3, MotorType.kBrushless);  //frontleft
  CANSparkMax backLeft = new CANSparkMax(4, MotorType.kBrushless); //backleft
  // intake motors
  CANSparkMax intake1 = new CANSparkMax(5, MotorType.kBrushed); //inner
  CANSparkMax intake2 = new CANSparkMax(6, MotorType.kBrushed); //outer
  // Shooter motors (y-split)
  CANSparkMax shooter1 = new CANSparkMax(7, MotorType.kBrushed);
  CANSparkMax shooter2 = new CANSparkMax(8, MotorType.kBrushed);
  // controllers
  XboxController driver = new XboxController(0);
  XboxController coPilot = new XboxController(1);
  // servo
  Servo flicker = new Servo(2);
  Servo Note = new Servo(0);
  // timer
  Timer timer1 = new Timer();
  //pneumatics
  DoubleSolenoid Armsolenoid = new DoubleSolenoid(PneumaticsModuleType.REVPH, 6, 7);
  boolean solenoidOpen = false;
  //drive limit
  double limit = .72;



  /**
   * This function is run when the robot is first started up and should be used for any
   * initialization code.
   */
  @Override
  public void robotInit() {
    m_chooser.setDefaultOption("Default Auto", kDefaultAuto);
    m_chooser.addOption("My Auto", kCustomAuto);
    m_chooser.addOption("open side", kopenside);
    SmartDashboard.putData("Auto choices", m_chooser);
    // camera use
    //CameraServer.startAutomaticCapture();
    // timer
    timer1.start();
  }

  /**
   * This function is called every 20 ms, no matter the mode. Use this for items like diagnostics
   * that you want ran during disabled, autonomous, teleoperated and test.
   *
   * <p>This runs after the mode specific periodic functions, but before LiveWindow and
   * SmartDashboard integrated updating.
   */
  @Override
  public void robotPeriodic() {}

  /**
   * This autonomous (along with the chooser code above) shows how to select between different
   * autonomous modes using the dashboard. The sendable chooser code works with the Java
   * SmartDashboard. If you prefer the LabVIEW Dashboard, remove all of the chooser code and
   * uncomment the getString line to get the auto name from the text box below the Gyro
   *
   * <p>You can add additional auto modes by adding additional comparisons to the switch structure
   * below with additional strings. If using the SendableChooser make sure to add them to the
   * chooser code above as well.
   */
  @Override
  public void autonomousInit() {
    m_autoSelected = m_chooser.getSelected();
    // m_autoSelected = SmartDashboard.getString("Auto Selector", kDefaultAuto);
    System.out.println("Auto selected: " + m_autoSelected);
    timer1.reset();
  }

  /** This function is called periodically during autonomous. */
  @Override
  public void autonomousPeriodic() {
  switch (m_autoSelected) { //how much time after is how long it will run
   case kCustomAuto:
    if(timer1.get() < 2.0){ //everything off
      shooter1.set(0);
      shooter2.set(0);
      intake1.set(0);
      intake2.set(0);
      flicker.setPosition(1);
      // drive
      frontLeft.set(0);
      backLeft.set(0);
      frontRight.set(0);
      backRight.set(0);
    }else if(timer1.get() < 2.6){ //spin up shooter
      shooter1.set(-.98);
      shooter2.set(.98);
    }else if(timer1.get() < 3.1){ //flick preload
      flicker.setPosition(.3);
    }else if(timer1.get() < 3.6){ //pause after shooting preload and start intake
      shooter1.set(0);
      shooter2.set(0);
      flicker.setPosition(1);
      intake1.set(.80);
      intake2.set(-.60);
      //drive
      frontLeft.set(0);
      backLeft.set(0);
      frontRight.set(0);
      backRight.set(0);
    }else if(timer1.get() < 4.3){ //drive toward NOTE
      frontLeft.set(-.80);
      backLeft.set(-.80);
      frontRight.set(.80);
      backRight.set(.80);
    }else if(timer1.get() < 4.6){ //pause after grabbing note
      shooter1.set(0);
      shooter2.set(0);
      //drive
      frontLeft.set(0);
      backLeft.set(0);
      frontRight.set(0);
      backRight.set(0);
      flicker.setPosition(1);
    }else if(timer1.get() < 4.7){ //drive up to shoot
      frontLeft.set(.40);
      backLeft.set(.40);
      frontRight.set(-.40);
      backRight.set(-.40);
      intake1.set(0);
      intake2.set(0);
    }else if(timer1.get() < 5.2){ //drive stops and spins up shooter
      frontLeft.set(0);
      backLeft.set(0);
      frontRight.set(0);
      backRight.set(0);
      shooter1.set(-68);
      shooter2.set(-68);
    }else if(timer1.get() < 5.6){
      flicker.setPosition(.4);
    }else if(timer1.get() < 6.0){ //pause after shooting
      shooter1.set(0);
      shooter2.set(0);
      intake1.set(0);
      intake2.set(0);
      flicker.setPosition(1);
      //drive
      frontLeft.set(0);
      backLeft.set(0);
      frontRight.set(0);
      backRight.set(0);
    }
   break;
   case kopenside:  //open side 
    if(timer1.get() < 2.0){ //everything off
      shooter1.set(0);
      shooter2.set(0);
      intake1.set(0);
      intake2.set(0);
      flicker.setPosition(1);
      // drive
      frontLeft.set(0);
      backLeft.set(0);
      frontRight.set(0);
      backRight.set(0);
    }else if(timer1.get() < 2.8){ //spin up shooter
      shooter1.set(-.98);
      shooter2.set(.98);
    }else if(timer1.get() < 3.4){ //flick preload
      flicker.setPosition(.3);
    }else if(timer1.get() < 6.0){ //pause after shooting preload and start intake
      shooter1.set(0);
      shooter2.set(0);
      flicker.setPosition(1);
      //drive
      frontLeft.set(0);
      backLeft.set(0);
      frontRight.set(0);
      backRight.set(0);
    }else if(timer1.get() < 6.4){ //drive toward NOTE
      frontLeft.set(-.35);
      backLeft.set(-.35);
      frontRight.set(.35);
      backRight.set(.35);
    }else if(timer1.get() < 7.0){ //pause after grabbing note
      shooter1.set(0);
      shooter2.set(0);
      //drive
      frontLeft.set(0);
      backLeft.set(0);
      frontRight.set(0);
      backRight.set(0);
      flicker.setPosition(1);
    }else if(timer1.get() < 7.3){ //turn
      frontLeft.set(.35);
      backLeft.set(.35);
      frontRight.set(.35);
      backRight.set(.35);
    }else if(timer1.get() < 7.8){ //drive more back
      frontLeft.set(-.35);
      backLeft.set(-.35);
      frontRight.set(.35);
      backRight.set(.35);
    }else if(timer1.get() <9.0){
      shooter1.set(0);
      shooter2.set(0);
      flicker.setPosition(1);
      // drive
      frontLeft.set(0);
      backLeft.set(0);
      frontRight.set(0);
      backRight.set(0);
    }
   break;
   case kDefaultAuto: //amp side
   default:
    if(timer1.get() < 2.0){ //everything off
      shooter1.set(0);
      shooter2.set(0);
      intake1.set(0);
      intake2.set(0);
      flicker.setPosition(1);
      // drive
      frontLeft.set(0);
      backLeft.set(0);
      frontRight.set(0);
      backRight.set(0);
    }else if(timer1.get() < 2.3){
      shooter1.set(.80);
      shooter2.set(-.80);
    }else if(timer1.get() < 2.8){ //spin up shooter
      shooter1.set(-.98);
      shooter2.set(.98);
    }else if(timer1.get() < 3.4){ //flick preload
      flicker.setPosition(.3);
    }else if(timer1.get() < 3.8){ //flick preload
      flicker.setPosition(1);
    }else if(timer1.get() < 4.2){ //flick preload
      flicker.setPosition(.3);
    }else if(timer1.get() < 6.0){ //pause after shooting preload and start intake
      shooter1.set(0);
      shooter2.set(0);
      flicker.setPosition(1);
      //drive
      frontLeft.set(0);
      backLeft.set(0);
      frontRight.set(0);
      backRight.set(0);
    }else if(timer1.get() < 6.4){ //drive toward NOTE
      frontLeft.set(-.35);
      backLeft.set(-.35);
      frontRight.set(.35);
      backRight.set(.35);
    }else if(timer1.get() < 7.0){ //pause after grabbing note
      shooter1.set(0);
      shooter2.set(0);
      intake1.set(0);
      intake2.set(0);
      //drive
      frontLeft.set(0);
      backLeft.set(0);
      frontRight.set(0);
      backRight.set(0);
      flicker.setPosition(1);
    }else if(timer1.get() < 7.3){ //turn
      frontLeft.set(-.35);
      backLeft.set(-.35);
      frontRight.set(-.35);
      backRight.set(-.35);
    }else if(timer1.get() < 7.8){ //drive more back
      frontLeft.set(-.65);
      backLeft.set(-.65);
      frontRight.set(.65);
      backRight.set(.65);
    }else if(timer1.get() <8.0){
      shooter1.set(0);
      shooter2.set(0);
      intake1.set(0);
      intake2.set(0);
      flicker.setPosition(1);
      // drive
      frontLeft.set(0);
      backLeft.set(0);
      frontRight.set(0);
      backRight.set(0);
    }
   break;
  }

}

  /** This function is called once when teleop is enabled. */
  @Override
  public void teleopInit() {}

  /** This function is called periodically during operator control. */
  @Override
  public void teleopPeriodic() {

   
    frontLeft.set(MathUtil.applyDeadband(-driver.getLeftY()*limit, 0.08));
    backLeft.set(MathUtil.applyDeadband(-driver.getLeftY()*limit, 0.08));
    frontRight.set(MathUtil.applyDeadband(driver.getRightY()*limit, 0.08));
    backRight.set(MathUtil.applyDeadband(driver.getRightY()*limit, 0.08));
    //tank drive
  if(driver.getRightStickButton()){
    limit = .72;
  }else if(driver.getLeftStickButton()){
    limit = .50;
  }
   //strafe code
  if(driver.getRightBumper()){
   frontLeft.set(-.70);
   backLeft.set(.80);
   frontRight.set(.60);
   backRight.set(-.70);
  } else if(driver.getLeftBumper()){      
   frontLeft.set(.70);
   backLeft.set(-.80);
   frontRight.set(-.60);
   backRight.set(.70);
  } else 
  //intake controls
  if(driver.getRightTriggerAxis() >= 0.15){
   intake1.set(.80);  //pos
   intake2.set(-.60);
  }else if(driver.getRightTriggerAxis() <0.05){
   intake1.stopMotor();
   intake2.stopMotor();
  }
  // else if (driver.getLeftTriggerAxis()>=0.15){
    // intake1.set(-0.80);
    // intake2.set(0.60);
  // } else if (driver.getLeftTriggerAxis() <0.05){
    // intake1.stopMotor();
    // intake1.stopMotor();
  // }

  //if(driver.getLeftTriggerAxis() >= 0.15){  //outtake
  //  intake1.set(-.80);
  //  intake2.set(.60);
  //}else if(driver.getLeftTriggerAxis() < 0.05){
  //  intake1.set(0);
  //  intake2.set(0);
  //}

   //pneumatic controls
  if(driver.getBButton()){  //pneumatics go down
    solenoidOpen = !solenoidOpen;
  }   
  if(solenoidOpen){
    Armsolenoid.set(Value.kForward);  
  } else if (driver.getAButton()){  //pneumatics go up
    Armsolenoid.set(Value.kReverse);
  } else{
    Armsolenoid.set(Value.kOff);
  }
  //Note dropper servo
  if(coPilot.getRightTriggerAxis() >= .15){
    Note.setPosition(.5);
  }else{
    Note.setPosition(1);
  }
  //shooter controls
  if(coPilot.getYButton()){
   shooter1.set(-.98);
   shooter2.set(.98);
  }
  if(coPilot.getXButton()){
   shooter1.set(0);
   shooter2.set(0);
  }
  //flicker controls
  if(coPilot.getRightBumper()){
    flicker.setPosition(.6);
  }else {
    flicker.setPosition(1);
  }
  }

  /** This function is called once when the robot is disabled. */
  @Override
  public void disabledInit() {}

  /** This function is called periodically when disabled. */
  @Override
  public void disabledPeriodic() {}

  /** This function is called once when test mode is enabled. */
  @Override
  public void testInit() {}

  /** This function is called periodically during test mode. */
  @Override
  public void testPeriodic() {}

  /** This function is called once when the robot is first started up. */
  @Override
  public void simulationInit() {}

  /** This function is called periodically whilst in simulation. */
  @Override
  public void simulationPeriodic() {}
}
