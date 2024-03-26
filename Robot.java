package frc.robot;

import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
// added imports below
import edu.wpi.first.wpilibj.Servo;
import com.revrobotics.CANSparkLowLevel.MotorType;
import com.revrobotics.CANSparkMax;
import edu.wpi.first.cameraserver.CameraServer;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.PneumaticsModuleType;
import edu.wpi.first.wpilibj.DoubleSolenoid.Value;


public class Robot extends TimedRobot {
  private static final String kDefaultAuto = "Default";
  private static final String kCustomAuto = "My Auto";
  private String m_autoSelected;
  private final SendableChooser<String> m_chooser = new SendableChooser<>();
  // drive motors
  CANSparkMax frontRight = new CANSparkMax(1, MotorType.kBrushless); //frontright
  CANSparkMax backRight = new CANSparkMax(2, MotorType.kBrushless);  //backright
  CANSparkMax frontLeft = new CANSparkMax(3, MotorType.kBrushless);  //frontleft
  CANSparkMax backLeft = new CANSparkMax(4, MotorType.kBrushless); //backleft
  // intake motors
  CANSparkMax intake1 = new CANSparkMax(5, MotorType.kBrushed);
  CANSparkMax intake2 = new CANSparkMax(6, MotorType.kBrushed);
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
  double limit = .90;



  /**
   * This function is run when the robot is first started up and should be used for any
   * initialization code.
   */
  @Override
  public void robotInit() {
    m_chooser.setDefaultOption("Default Auto", kDefaultAuto);
    m_chooser.addOption("My Auto", kCustomAuto);
    SmartDashboard.putData("Auto choices", m_chooser);
    // camera use
    CameraServer.startAutomaticCapture();
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
  switch (m_autoSelected) {
   case kCustomAuto:
    if(timer1.get() < 2.0){ //everything off
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
    }else if(timer1.get() < 2.4){ //spin up shooter
      shooter1.set(-.98);
      shooter2.set(.98);
    }else if(timer1.get() < 2.6){ //flick preload
      flicker.setPosition(.5);
    }else if(timer1.get() < 2.7){ //pause after shooting preload
      shooter1.set(0);
      shooter2.set(0);
      intake1.set(0);
      intake2.set(0);
      //drive
      frontLeft.set(0);
      backLeft.set(0);
      frontRight.set(0);
      backRight.set(0);
    }else if(timer1.get() < 3.4){ //intake on and drive toward NOTE
      //intake1.set(.98);
      //intake2.set(-.98);
      frontLeft.set(-.25);
      backLeft.set(-.25);
      frontRight.set(.25);
      backRight.set(.25);
    }
    else if(timer1.get() < 3.8){ //pause after grabbing note
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
    }
    //else if(timer1.get() < 4.0){ //drive up to shoot
    //  frontLeft.set(-.25);
    //  backLeft.set(-.25);
    //  frontRight.set(.25);
    //  backRight.set(.25);
    //}else if(timer1.get() < 4.2){ //drive stops and spins up shooter
    //  frontLeft.set(0);
    //  backLeft.set(0);
    //  frontRight.set(0);
    //  backRight.set(0);
    //  shooter1.set(-68);
    //  shooter2.set(-68);
    //}else if(timer1.get() < 4.4){ //pause after shooting
    //  shooter1.set(0);
    //  shooter2.set(0);
    //  intake1.set(0);
    //  intake2.set(0);
    //  //drive
    //  frontLeft.set(0);
    //  backLeft.set(0);
    //  frontRight.set(0);
    //  backRight.set(0);
    //}else if(timer1.get() < 4.6)
      
    // Put custom auto code here
   break;
   case kDefaultAuto:
   default:

   break;
  
  }

}

  /** This function is called once when teleop is enabled. */
  @Override
  public void teleopInit() {}

  /** This function is called periodically during operator control. */
  @Override
  public void teleopPeriodic() {
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
  } else { //tank drive part
    if(driver.getRightStickButton()){
      limit = .90;
    }else if(driver.getLeftStickButton()){
      limit = .45;
    }
   frontLeft.set(-driver.getLeftY()*limit);
   backLeft.set(-driver.getLeftY()*limit);
   frontRight.set(driver.getRightY()*limit);
   backRight.set(driver.getRightY()*limit);
  }
  //Note dropper servo
  if(driver.getYButton()){
    Note.setPosition(.5);
  }else{
    Note.setPosition(1);
  }
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
  //intake controls
  if(coPilot.getAButtonPressed()){
   intake1.set(.60);  //pos
   intake2.set(-.60);
  }else if(coPilot.getAButtonReleased()){
   intake1.set(0);
   intake2.set(0);
  }
  if(coPilot.getBButtonPressed()){  //outtake
    intake1.set(-.60);
    intake2.set(.60);
  }else if(coPilot.getBButtonReleased()){
    intake1.set(0);
    intake2.set(0);
  }
  //shooter controls
  if(coPilot.getYButton()){
   shooter1.set(-.80);
   shooter2.set(.80);
  }
  if(coPilot.getXButton()){
   shooter1.set(0);
   shooter2.set(0);
  }
  //flicker controls
  if(coPilot.getRightBumper()){
    flicker.setPosition(-5);
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
