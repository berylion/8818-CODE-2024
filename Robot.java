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
import edu.wpi.first.wpilibj.motorcontrol.Victor;
import edu.wpi.first.wpilibj.Timer;


public class Robot extends TimedRobot {
  private static final String kDefaultAuto = "Default";
  private static final String kCustomAuto = "My Auto";
  private String m_autoSelected;
  private final SendableChooser<String> m_chooser = new SendableChooser<>();
  // drive motors
  CANSparkMax frontRight = new CANSparkMax(1, MotorType.kBrushed); //frontright
  CANSparkMax backRight = new CANSparkMax(2, MotorType.kBrushed);  //backright
  CANSparkMax frontLeft = new CANSparkMax(3, MotorType.kBrushed);  //frontleft
  CANSparkMax backLeft = new CANSparkMax(4, MotorType.kBrushed); //backleft
  // intake motors
  CANSparkMax intake1 = new CANSparkMax(5, MotorType.kBrushed);// can id 5
  CANSparkMax intake2 = new CANSparkMax(6, MotorType.kBrushed);// cna id 6
  // Shooter motors (y-split)
  Victor shooter = new Victor(0);
  // controllers
  XboxController driver = new XboxController(0);
  XboxController coPilot = new XboxController(1);
  // servo
  Servo flicker = new Servo(1);//PWM port 1
  // timer
  Timer timer1 = new Timer();


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
  }

  /** This function is called periodically during autonomous. */
  @Override
  public void autonomousPeriodic() {
    switch (m_autoSelected) {
      case kCustomAuto:
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
  //drive controls (including strafe)
  if(driver.getRightBumper()){
   frontLeft.set(-.98);
   backLeft.set(.98);
   backLeft.setInverted(true);
   frontRight.set(.98);
   frontRight.setInverted(true);
   backRight.set(-.98);
  } else if(driver.getLeftBumper()){      
   frontLeft.set(.98);
   backLeft.set(-.98);
   frontLeft.setInverted(true);
   frontRight.set(-.98);
   backRight.setInverted(true);
   backRight.set(.98);
  } else { //tank drive part
   frontLeft.set(-driver.getLeftY());
   backLeft.set(-driver.getLeftY());
   frontRight.set(driver.getRightY());
   backRight.set(driver.getRightY());
  }
  //intake controls
  if(coPilot.getAButton()){
   intake1.set(.98);  //pos
   intake2.set(-.98);
  } else if(coPilot.getBButton()){
   intake1.set(0);
   intake2.set(0);
  }
  //shooter controls
  if(driver.getYButton()){
   shooter.set(-.68);
  }
  if(driver.getXButton()){
   shooter.set(0);
  }
  //flicker controls
  if (coPilot.getRightBumper()){
   flicker.setPosition(-.5);
  } else {
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
