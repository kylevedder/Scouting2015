/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package server.frames;

import client.frames.*;
import client.filemanager.ClientFileManager;
import client.objects.activedata.ActiveData;
import client.objects.activedata.FeedLocation;
import client.objects.activedata.RobotNumWheels;
import client.objects.activedata.RobotShape;
import client.objects.activedata.RobotWheelType;
import client.objects.activedata.TotePickupOrientation;
import java.util.Enumeration;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.AbstractButton;
import javax.swing.ButtonGroup;
import javax.swing.DefaultListModel;
import javax.swing.ListModel;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import main.Main;
import client.objects.matchdata.CoOpType;
import client.objects.matchdata.HumanPlayerType;
import client.objects.matchdata.MatchData;
import client.objects.matchdata.RobotActivityType;
import client.objects.stacks.StackBase;
import client.objects.stacks.StackContainer;
import client.objects.stacks.StackTote;
import utils.Utils;

/**
 *
 * @author kyle
 */
public class ActiveViewerFrame extends javax.swing.JFrame
{

    private static final int SCROLL_SPEED = 16;

    /**
     * Creates new form MatchFrame
     */
    public ActiveViewerFrame(ActiveData activeData)
    {
        initComponents();
        try
        {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
            SwingUtilities.updateComponentTreeUI(this);
            this.setLocationRelativeTo(null);
            this.pack();
        }
        catch (ClassNotFoundException ex)
        {
            Logger.getLogger(ActiveViewerFrame.class.getName()).log(Level.SEVERE, null, ex);
        }
        catch (InstantiationException ex)
        {
            Logger.getLogger(ActiveViewerFrame.class.getName()).log(Level.SEVERE, null, ex);
        }
        catch (IllegalAccessException ex)
        {
            Logger.getLogger(ActiveViewerFrame.class.getName()).log(Level.SEVERE, null, ex);
        }
        catch (UnsupportedLookAndFeelException ex)
        {
            Logger.getLogger(ActiveViewerFrame.class.getName()).log(Level.SEVERE, null, ex);
        }
        if (activeData != null)
        {
            resetFrame(activeData);
        }
    }

    /**
     * Resets the frame to its given value
     *
     * @param activeData
     */
    public void resetFrame(ActiveData activeData)
    {
        fieldScouter.setText(activeData.getMatchRobotScouter());
        fieldTeamNum.setText(String.valueOf(activeData.getMatchTeamNumber()));        

        CoOpType coopType = activeData.getCoopType();

        switch (coopType)
        {
            case NONE:
                radioCoopNone.setSelected(true);
                radioCoopUnstacked.setSelected(false);
                radioCoopStacked.setSelected(false);
                break;
            case STACKED:
                radioCoopNone.setSelected(false);
                radioCoopUnstacked.setSelected(false);
                radioCoopStacked.setSelected(true);
                break;
            case UNSTACKED:
                radioCoopNone.setSelected(false);
                radioCoopUnstacked.setSelected(true);
                radioCoopStacked.setSelected(false);
                break;
        }
        

        HumanPlayerType humanPlayerType = activeData.getHumanPlayerType();

        switch (humanPlayerType)
        {
            case EXCELLENT:
                checkBoxHPThrewNoodles.setSelected(true);
                radioHPExcellent.setSelected(true);
                radioHPMediocre.setSelected(false);
                radioHPPoor.setSelected(false);
                break;

            case MEDIOCRE:
                checkBoxHPThrewNoodles.setSelected(true);
                radioHPExcellent.setSelected(false);
                radioHPMediocre.setSelected(true);
                radioHPPoor.setSelected(false);
                break;

            case POOR:
                checkBoxHPThrewNoodles.setSelected(true);
                radioHPExcellent.setSelected(false);
                radioHPMediocre.setSelected(false);
                radioHPPoor.setSelected(true);
                break;

            case NO_THROW:
                checkBoxHPThrewNoodles.setSelected(false);
                radioHPExcellent.setSelected(false);
                radioHPMediocre.setSelected(false);
                radioHPPoor.setSelected(false);
                break;
        }
        
        spinnerNumContainers.setValue(activeData.getAutoNumContainers());
        spinnerNumTotes.setValue(activeData.getAutoNumTotes());
        checkBoxInAutoZone.setSelected(activeData.getAutoInAutoZone());
        checkBoxTotesStacked.setEnabled(activeData.getAutoTotesStacked());                
                
        textBoxRobotShape.setText(activeData.getRobotShape().toString());        
        textBoxRobotDriveTrain.setText(activeData.getRobotNumWheels().toString().replaceFirst("WHEEL_", ""));
        textBoxRobotWheelType.setText(activeData.getRobotWheelType().toString());
        textAreaRobotConfigComments.setText(activeData.getRobotComments());
        
        textBoxTeleopToteFeedLocation.setText(activeData.getToteFeedLocation().toString());        
        textBoxTeleopTotePickupOrientation.setText(activeData.getTotePickupOrientation().toString());
        spinnerMaxHeightContainer.setValue(activeData.getContainerMaxCappableStackHeight());
        spinnerMaxNumTotes.setValue(activeData.getToteMaxStackHeight());
        
        checkBoxTeleopLitterPickupLitter.setSelected(activeData.getLitterCanPickupLitter());
        checkBoxTeleopLitterPickupLitter.setEnabled(true);
        checkBoxTeleopLitterPushLitter.setSelected(activeData.getLitterCanPushLitter());
        checkBoxTeleopLitterPushLitter.setEnabled(true);
        
        checkBoxTeleopTotesCanPickup.setSelected(activeData.getToteCanGetTotes());
        
        checkBoxTeleopContainersUpright.setSelected(activeData.getContainerMustBeUpright());
        checkBoxTeleopContainersPickup.setSelected(activeData.getContainerCanGetContainers());
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents()
    {

        buttonGroupCoop = new javax.swing.ButtonGroup();
        buttonGroupHumanPlayer = new javax.swing.ButtonGroup();
        mainPanelScroll = new javax.swing.JScrollPane();
        mainPanel = new javax.swing.JPanel();
        labelTitle = new javax.swing.JLabel();
        panelTeamInfo = new javax.swing.JPanel();
        labelTeamNum = new javax.swing.JLabel();
        fieldTeamNum = new javax.swing.JTextField();
        labelScouter = new javax.swing.JLabel();
        fieldScouter = new javax.swing.JTextField();
        panelRobotConfig = new javax.swing.JPanel();
        panelRobotShape = new javax.swing.JPanel();
        textBoxRobotShape = new javax.swing.JTextField();
        panelRobotActivityComments = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        textAreaRobotConfigComments = new javax.swing.JTextArea();
        panelRobotDriveTrain = new javax.swing.JPanel();
        textBoxRobotDriveTrain = new javax.swing.JTextField();
        panelRobotWheelType = new javax.swing.JPanel();
        textBoxRobotWheelType = new javax.swing.JTextField();
        panelScoring = new javax.swing.JPanel();
        panelAutonomous = new javax.swing.JPanel();
        panelAutoScoredTotes = new javax.swing.JPanel();
        labelNumTotes = new javax.swing.JLabel();
        spinnerNumTotes = new javax.swing.JSpinner();
        checkBoxTotesStacked = new javax.swing.JCheckBox();
        panelAutoScoredContainer = new javax.swing.JPanel();
        labelNumContainers = new javax.swing.JLabel();
        spinnerNumContainers = new javax.swing.JSpinner();
        panelAutoScoredRobot = new javax.swing.JPanel();
        checkBoxInAutoZone = new javax.swing.JCheckBox();
        panelTeleop = new javax.swing.JPanel();
        panelTeleopLitter = new javax.swing.JPanel();
        checkBoxTeleopLitterPushLitter = new javax.swing.JCheckBox();
        checkBoxTeleopLitterPickupLitter = new javax.swing.JCheckBox();
        panelTeleopTotes = new javax.swing.JPanel();
        labelTeleopTotesMaxHeight = new javax.swing.JLabel();
        spinnerMaxNumTotes = new javax.swing.JSpinner();
        checkBoxTeleopTotesCanPickup = new javax.swing.JCheckBox();
        labelPickupOrintation = new javax.swing.JLabel();
        labelFeedLocation = new javax.swing.JLabel();
        textBoxTeleopTotePickupOrientation = new javax.swing.JTextField();
        textBoxTeleopToteFeedLocation = new javax.swing.JTextField();
        panelTeleopContainer = new javax.swing.JPanel();
        checkBoxTeleopContainersPickup = new javax.swing.JCheckBox();
        checkBoxTeleopContainersUpright = new javax.swing.JCheckBox();
        spinnerMaxHeightContainer = new javax.swing.JSpinner();
        labelTeleopContainersMaxHeight = new javax.swing.JLabel();
        panelCoop = new javax.swing.JPanel();
        radioCoopNone = new javax.swing.JRadioButton();
        radioCoopUnstacked = new javax.swing.JRadioButton();
        radioCoopStacked = new javax.swing.JRadioButton();
        spinnerMaxCoopStack = new javax.swing.JSpinner();
        labeCoopMaxStackHeight = new javax.swing.JLabel();
        panelHumanPlayer = new javax.swing.JPanel();
        radioHPPoor = new javax.swing.JRadioButton();
        radioHPMediocre = new javax.swing.JRadioButton();
        radioHPExcellent = new javax.swing.JRadioButton();
        checkBoxHPThrewNoodles = new javax.swing.JCheckBox();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Active Viewer");
        setResizable(false);

        mainPanelScroll.setVerticalScrollBarPolicy(javax.swing.ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);

        labelTitle.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        labelTitle.setText("Active Viewer");

        panelTeamInfo.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createEtchedBorder(), "Team Info", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 1, 18))); // NOI18N

        labelTeamNum.setText("Team #:");

        fieldTeamNum.addKeyListener(new java.awt.event.KeyAdapter()
        {
            public void keyReleased(java.awt.event.KeyEvent evt)
            {
                fieldTeamNumKeyReleased(evt);
            }
        });

        labelScouter.setText("Scouter:");

        javax.swing.GroupLayout panelTeamInfoLayout = new javax.swing.GroupLayout(panelTeamInfo);
        panelTeamInfo.setLayout(panelTeamInfoLayout);
        panelTeamInfoLayout.setHorizontalGroup(
            panelTeamInfoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelTeamInfoLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(labelTeamNum)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(fieldTeamNum, javax.swing.GroupLayout.PREFERRED_SIZE, 48, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(labelScouter)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(fieldScouter, javax.swing.GroupLayout.PREFERRED_SIZE, 129, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        panelTeamInfoLayout.setVerticalGroup(
            panelTeamInfoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, panelTeamInfoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                .addComponent(fieldTeamNum, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addComponent(labelTeamNum)
                .addComponent(labelScouter)
                .addComponent(fieldScouter, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        panelRobotConfig.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createEtchedBorder(), "Robot Configuration", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 1, 18))); // NOI18N

        panelRobotShape.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createEtchedBorder(), "Robot Shape", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 1, 11))); // NOI18N

        javax.swing.GroupLayout panelRobotShapeLayout = new javax.swing.GroupLayout(panelRobotShape);
        panelRobotShape.setLayout(panelRobotShapeLayout);
        panelRobotShapeLayout.setHorizontalGroup(
            panelRobotShapeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(textBoxRobotShape)
        );
        panelRobotShapeLayout.setVerticalGroup(
            panelRobotShapeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelRobotShapeLayout.createSequentialGroup()
                .addComponent(textBoxRobotShape, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 12, Short.MAX_VALUE))
        );

        panelRobotActivityComments.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createEtchedBorder(), "Comments (Optional)", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 1, 11))); // NOI18N

        textAreaRobotConfigComments.setColumns(20);
        textAreaRobotConfigComments.setRows(1);
        jScrollPane1.setViewportView(textAreaRobotConfigComments);

        javax.swing.GroupLayout panelRobotActivityCommentsLayout = new javax.swing.GroupLayout(panelRobotActivityComments);
        panelRobotActivityComments.setLayout(panelRobotActivityCommentsLayout);
        panelRobotActivityCommentsLayout.setHorizontalGroup(
            panelRobotActivityCommentsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane1)
        );
        panelRobotActivityCommentsLayout.setVerticalGroup(
            panelRobotActivityCommentsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane1)
        );

        panelRobotDriveTrain.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createEtchedBorder(), "Robot Drive Train", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 1, 11))); // NOI18N

        javax.swing.GroupLayout panelRobotDriveTrainLayout = new javax.swing.GroupLayout(panelRobotDriveTrain);
        panelRobotDriveTrain.setLayout(panelRobotDriveTrainLayout);
        panelRobotDriveTrainLayout.setHorizontalGroup(
            panelRobotDriveTrainLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(textBoxRobotDriveTrain, javax.swing.GroupLayout.DEFAULT_SIZE, 420, Short.MAX_VALUE)
        );
        panelRobotDriveTrainLayout.setVerticalGroup(
            panelRobotDriveTrainLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(textBoxRobotDriveTrain, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        );

        panelRobotWheelType.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createEtchedBorder(), "Robot Wheel Type", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 1, 11))); // NOI18N

        javax.swing.GroupLayout panelRobotWheelTypeLayout = new javax.swing.GroupLayout(panelRobotWheelType);
        panelRobotWheelType.setLayout(panelRobotWheelTypeLayout);
        panelRobotWheelTypeLayout.setHorizontalGroup(
            panelRobotWheelTypeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(textBoxRobotWheelType)
        );
        panelRobotWheelTypeLayout.setVerticalGroup(
            panelRobotWheelTypeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(textBoxRobotWheelType, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        );

        javax.swing.GroupLayout panelRobotConfigLayout = new javax.swing.GroupLayout(panelRobotConfig);
        panelRobotConfig.setLayout(panelRobotConfigLayout);
        panelRobotConfigLayout.setHorizontalGroup(
            panelRobotConfigLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelRobotConfigLayout.createSequentialGroup()
                .addGroup(panelRobotConfigLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addComponent(panelRobotDriveTrain, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(panelRobotShape, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panelRobotConfigLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(panelRobotActivityComments, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(panelRobotWheelType, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
        );
        panelRobotConfigLayout.setVerticalGroup(
            panelRobotConfigLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelRobotConfigLayout.createSequentialGroup()
                .addGroup(panelRobotConfigLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addComponent(panelRobotActivityComments, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(panelRobotShape, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(panelRobotConfigLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(panelRobotWheelType, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(panelRobotDriveTrain, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(34, 34, 34))
        );

        panelScoring.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createEtchedBorder(), "Scoring", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 1, 18))); // NOI18N

        panelAutonomous.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createEtchedBorder(), "Autonomous", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 1, 14))); // NOI18N

        panelAutoScoredTotes.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createEtchedBorder(), "Scored Totes", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 1, 11))); // NOI18N

        labelNumTotes.setText("Scored Totes:");

        spinnerNumTotes.addChangeListener(new javax.swing.event.ChangeListener()
        {
            public void stateChanged(javax.swing.event.ChangeEvent evt)
            {
                spinnerNumTotesStateChanged(evt);
            }
        });
        spinnerNumTotes.addPropertyChangeListener(new java.beans.PropertyChangeListener()
        {
            public void propertyChange(java.beans.PropertyChangeEvent evt)
            {
                spinnerNumTotesPropertyChange(evt);
            }
        });

        checkBoxTotesStacked.setText("Totes Stacked?");
        checkBoxTotesStacked.setEnabled(false);

        javax.swing.GroupLayout panelAutoScoredTotesLayout = new javax.swing.GroupLayout(panelAutoScoredTotes);
        panelAutoScoredTotes.setLayout(panelAutoScoredTotesLayout);
        panelAutoScoredTotesLayout.setHorizontalGroup(
            panelAutoScoredTotesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelAutoScoredTotesLayout.createSequentialGroup()
                .addGroup(panelAutoScoredTotesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(panelAutoScoredTotesLayout.createSequentialGroup()
                        .addComponent(labelNumTotes)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(spinnerNumTotes, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(checkBoxTotesStacked))
                .addGap(0, 78, Short.MAX_VALUE))
        );
        panelAutoScoredTotesLayout.setVerticalGroup(
            panelAutoScoredTotesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelAutoScoredTotesLayout.createSequentialGroup()
                .addGroup(panelAutoScoredTotesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(labelNumTotes)
                    .addComponent(spinnerNumTotes, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(checkBoxTotesStacked)
                .addGap(0, 0, Short.MAX_VALUE))
        );

        panelAutoScoredContainer.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createEtchedBorder(), "Scored Containers", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 1, 11))); // NOI18N

        labelNumContainers.setText("Scored Containers:");

        spinnerNumContainers.addChangeListener(new javax.swing.event.ChangeListener()
        {
            public void stateChanged(javax.swing.event.ChangeEvent evt)
            {
                spinnerNumContainersStateChanged(evt);
            }
        });

        javax.swing.GroupLayout panelAutoScoredContainerLayout = new javax.swing.GroupLayout(panelAutoScoredContainer);
        panelAutoScoredContainer.setLayout(panelAutoScoredContainerLayout);
        panelAutoScoredContainerLayout.setHorizontalGroup(
            panelAutoScoredContainerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelAutoScoredContainerLayout.createSequentialGroup()
                .addComponent(labelNumContainers)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(spinnerNumContainers, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 53, Short.MAX_VALUE))
        );
        panelAutoScoredContainerLayout.setVerticalGroup(
            panelAutoScoredContainerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelAutoScoredContainerLayout.createSequentialGroup()
                .addGroup(panelAutoScoredContainerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(labelNumContainers)
                    .addComponent(spinnerNumContainers, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(0, 0, Short.MAX_VALUE))
        );

        panelAutoScoredRobot.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createEtchedBorder(), "Scored Robot", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 1, 11))); // NOI18N

        checkBoxInAutoZone.setText("In Auto Zone?");

        javax.swing.GroupLayout panelAutoScoredRobotLayout = new javax.swing.GroupLayout(panelAutoScoredRobot);
        panelAutoScoredRobot.setLayout(panelAutoScoredRobotLayout);
        panelAutoScoredRobotLayout.setHorizontalGroup(
            panelAutoScoredRobotLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelAutoScoredRobotLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(checkBoxInAutoZone)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        panelAutoScoredRobotLayout.setVerticalGroup(
            panelAutoScoredRobotLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelAutoScoredRobotLayout.createSequentialGroup()
                .addComponent(checkBoxInAutoZone)
                .addGap(0, 0, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout panelAutonomousLayout = new javax.swing.GroupLayout(panelAutonomous);
        panelAutonomous.setLayout(panelAutonomousLayout);
        panelAutonomousLayout.setHorizontalGroup(
            panelAutonomousLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelAutonomousLayout.createSequentialGroup()
                .addComponent(panelAutoScoredTotes, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(panelAutoScoredContainer, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(panelAutoScoredRobot, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        panelAutonomousLayout.setVerticalGroup(
            panelAutonomousLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(panelAutoScoredTotes, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(panelAutoScoredContainer, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(panelAutoScoredRobot, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        panelTeleop.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createEtchedBorder(), "Teleoperated", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 1, 14))); // NOI18N

        panelTeleopLitter.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createEtchedBorder(), "Litter", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 1, 11))); // NOI18N

        checkBoxTeleopLitterPushLitter.setText("Can Push Litter?");

        checkBoxTeleopLitterPickupLitter.setText("Can Pick Up Litter?");

        javax.swing.GroupLayout panelTeleopLitterLayout = new javax.swing.GroupLayout(panelTeleopLitter);
        panelTeleopLitter.setLayout(panelTeleopLitterLayout);
        panelTeleopLitterLayout.setHorizontalGroup(
            panelTeleopLitterLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelTeleopLitterLayout.createSequentialGroup()
                .addGroup(panelTeleopLitterLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(checkBoxTeleopLitterPickupLitter, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(checkBoxTeleopLitterPushLitter, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(0, 0, Short.MAX_VALUE))
        );
        panelTeleopLitterLayout.setVerticalGroup(
            panelTeleopLitterLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelTeleopLitterLayout.createSequentialGroup()
                .addComponent(checkBoxTeleopLitterPushLitter)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(checkBoxTeleopLitterPickupLitter)
                .addGap(0, 0, Short.MAX_VALUE))
        );

        panelTeleopTotes.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createEtchedBorder(), "Totes", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 1, 11))); // NOI18N

        labelTeleopTotesMaxHeight.setText("Max Stack Height:");

        spinnerMaxNumTotes.addChangeListener(new javax.swing.event.ChangeListener()
        {
            public void stateChanged(javax.swing.event.ChangeEvent evt)
            {
                spinnerMaxNumTotesStateChanged(evt);
            }
        });
        spinnerMaxNumTotes.addPropertyChangeListener(new java.beans.PropertyChangeListener()
        {
            public void propertyChange(java.beans.PropertyChangeEvent evt)
            {
                spinnerMaxNumTotesPropertyChange(evt);
            }
        });

        checkBoxTeleopTotesCanPickup.setText("Can Pick Up Totes?");
        checkBoxTeleopTotesCanPickup.addActionListener(new java.awt.event.ActionListener()
        {
            public void actionPerformed(java.awt.event.ActionEvent evt)
            {
                checkBoxTeleopTotesCanPickupActionPerformed(evt);
            }
        });

        labelPickupOrintation.setText("Pickup Orientation:");

        labelFeedLocation.setText("Feed Location:");

        javax.swing.GroupLayout panelTeleopTotesLayout = new javax.swing.GroupLayout(panelTeleopTotes);
        panelTeleopTotes.setLayout(panelTeleopTotesLayout);
        panelTeleopTotesLayout.setHorizontalGroup(
            panelTeleopTotesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelTeleopTotesLayout.createSequentialGroup()
                .addGroup(panelTeleopTotesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(panelTeleopTotesLayout.createSequentialGroup()
                        .addComponent(labelPickupOrintation)
                        .addGap(18, 18, 18)
                        .addComponent(textBoxTeleopTotePickupOrientation, javax.swing.GroupLayout.PREFERRED_SIZE, 72, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(labelTeleopTotesMaxHeight)
                    .addComponent(checkBoxTeleopTotesCanPickup)
                    .addGroup(panelTeleopTotesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                        .addGroup(panelTeleopTotesLayout.createSequentialGroup()
                            .addGap(0, 0, Short.MAX_VALUE)
                            .addComponent(spinnerMaxNumTotes, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGroup(panelTeleopTotesLayout.createSequentialGroup()
                            .addComponent(labelFeedLocation)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addComponent(textBoxTeleopToteFeedLocation))))
                .addGap(12, 12, 12))
        );
        panelTeleopTotesLayout.setVerticalGroup(
            panelTeleopTotesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelTeleopTotesLayout.createSequentialGroup()
                .addComponent(checkBoxTeleopTotesCanPickup)
                .addGap(1, 1, 1)
                .addGroup(panelTeleopTotesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(labelPickupOrintation)
                    .addComponent(textBoxTeleopTotePickupOrientation, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panelTeleopTotesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(labelTeleopTotesMaxHeight)
                    .addComponent(spinnerMaxNumTotes, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panelTeleopTotesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(labelFeedLocation)
                    .addComponent(textBoxTeleopToteFeedLocation, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(0, 45, Short.MAX_VALUE))
        );

        panelTeleopContainer.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createEtchedBorder(), "Containers", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 1, 11))); // NOI18N

        checkBoxTeleopContainersPickup.setText("Can Pick Up Containers?");
        checkBoxTeleopContainersPickup.addActionListener(new java.awt.event.ActionListener()
        {
            public void actionPerformed(java.awt.event.ActionEvent evt)
            {
                checkBoxTeleopContainersPickupActionPerformed(evt);
            }
        });

        checkBoxTeleopContainersUpright.setText("Must To Be Upright?");

        spinnerMaxHeightContainer.addChangeListener(new javax.swing.event.ChangeListener()
        {
            public void stateChanged(javax.swing.event.ChangeEvent evt)
            {
                spinnerMaxHeightContainerStateChanged(evt);
            }
        });
        spinnerMaxHeightContainer.addPropertyChangeListener(new java.beans.PropertyChangeListener()
        {
            public void propertyChange(java.beans.PropertyChangeEvent evt)
            {
                spinnerMaxHeightContainerPropertyChange(evt);
            }
        });

        labelTeleopContainersMaxHeight.setText("Max Capable Stack Height:");

        javax.swing.GroupLayout panelTeleopContainerLayout = new javax.swing.GroupLayout(panelTeleopContainer);
        panelTeleopContainer.setLayout(panelTeleopContainerLayout);
        panelTeleopContainerLayout.setHorizontalGroup(
            panelTeleopContainerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(checkBoxTeleopContainersPickup, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(panelTeleopContainerLayout.createSequentialGroup()
                .addComponent(labelTeleopContainersMaxHeight)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(spinnerMaxHeightContainer, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
            .addGroup(panelTeleopContainerLayout.createSequentialGroup()
                .addGap(21, 21, 21)
                .addComponent(checkBoxTeleopContainersUpright, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        panelTeleopContainerLayout.setVerticalGroup(
            panelTeleopContainerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelTeleopContainerLayout.createSequentialGroup()
                .addComponent(checkBoxTeleopContainersPickup)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(checkBoxTeleopContainersUpright)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panelTeleopContainerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(labelTeleopContainersMaxHeight)
                    .addComponent(spinnerMaxHeightContainer, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(0, 0, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout panelTeleopLayout = new javax.swing.GroupLayout(panelTeleop);
        panelTeleop.setLayout(panelTeleopLayout);
        panelTeleopLayout.setHorizontalGroup(
            panelTeleopLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelTeleopLayout.createSequentialGroup()
                .addComponent(panelTeleopTotes, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(panelTeleopContainer, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(panelTeleopLitter, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        panelTeleopLayout.setVerticalGroup(
            panelTeleopLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(panelTeleopTotes, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(panelTeleopLitter, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(panelTeleopContainer, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout panelScoringLayout = new javax.swing.GroupLayout(panelScoring);
        panelScoring.setLayout(panelScoringLayout);
        panelScoringLayout.setHorizontalGroup(
            panelScoringLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(panelAutonomous, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(panelTeleop, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        panelScoringLayout.setVerticalGroup(
            panelScoringLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelScoringLayout.createSequentialGroup()
                .addComponent(panelAutonomous, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(panelTeleop, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        panelCoop.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createEtchedBorder(), "Coop", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 1, 14))); // NOI18N

        buttonGroupCoop.add(radioCoopNone);
        radioCoopNone.setText("None");

        buttonGroupCoop.add(radioCoopUnstacked);
        radioCoopUnstacked.setText("Unstacked");

        buttonGroupCoop.add(radioCoopStacked);
        radioCoopStacked.setText("Stacked");

        spinnerMaxCoopStack.addChangeListener(new javax.swing.event.ChangeListener()
        {
            public void stateChanged(javax.swing.event.ChangeEvent evt)
            {
                spinnerMaxCoopStackStateChanged(evt);
            }
        });
        spinnerMaxCoopStack.addPropertyChangeListener(new java.beans.PropertyChangeListener()
        {
            public void propertyChange(java.beans.PropertyChangeEvent evt)
            {
                spinnerMaxCoopStackPropertyChange(evt);
            }
        });

        labeCoopMaxStackHeight.setText("Max Stack Height:");

        javax.swing.GroupLayout panelCoopLayout = new javax.swing.GroupLayout(panelCoop);
        panelCoop.setLayout(panelCoopLayout);
        panelCoopLayout.setHorizontalGroup(
            panelCoopLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelCoopLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(panelCoopLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(panelCoopLayout.createSequentialGroup()
                        .addComponent(radioCoopUnstacked)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(labeCoopMaxStackHeight)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(spinnerMaxCoopStack, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(panelCoopLayout.createSequentialGroup()
                        .addGroup(panelCoopLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(radioCoopNone)
                            .addComponent(radioCoopStacked))
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        panelCoopLayout.setVerticalGroup(
            panelCoopLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelCoopLayout.createSequentialGroup()
                .addComponent(radioCoopNone)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panelCoopLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(radioCoopUnstacked)
                    .addComponent(spinnerMaxCoopStack, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(labeCoopMaxStackHeight))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(radioCoopStacked))
        );

        panelHumanPlayer.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createEtchedBorder(), "Human Player Noodle Throwing", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 1, 14))); // NOI18N

        buttonGroupHumanPlayer.add(radioHPPoor);
        radioHPPoor.setSelected(true);
        radioHPPoor.setText("Poor (0 scored noodles per match)");

        buttonGroupHumanPlayer.add(radioHPMediocre);
        radioHPMediocre.setText("Mediocre (1 to 2 scored noodles per match)");
        radioHPMediocre.setToolTipText("");

        buttonGroupHumanPlayer.add(radioHPExcellent);
        radioHPExcellent.setText("Excellent (3+ scored noodles per match)");
        radioHPExcellent.setActionCommand("Excellent");

        checkBoxHPThrewNoodles.setText("HP Throws Noodles");

        javax.swing.GroupLayout panelHumanPlayerLayout = new javax.swing.GroupLayout(panelHumanPlayer);
        panelHumanPlayer.setLayout(panelHumanPlayerLayout);
        panelHumanPlayerLayout.setHorizontalGroup(
            panelHumanPlayerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelHumanPlayerLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(checkBoxHPThrewNoodles)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(panelHumanPlayerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(radioHPPoor)
                    .addComponent(radioHPMediocre)
                    .addComponent(radioHPExcellent)))
        );
        panelHumanPlayerLayout.setVerticalGroup(
            panelHumanPlayerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelHumanPlayerLayout.createSequentialGroup()
                .addComponent(radioHPPoor)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panelHumanPlayerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(radioHPMediocre)
                    .addComponent(checkBoxHPThrewNoodles))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(radioHPExcellent))
        );

        javax.swing.GroupLayout mainPanelLayout = new javax.swing.GroupLayout(mainPanel);
        mainPanel.setLayout(mainPanelLayout);
        mainPanelLayout.setHorizontalGroup(
            mainPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(mainPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(mainPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(mainPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                        .addComponent(panelScoring, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(javax.swing.GroupLayout.Alignment.LEADING, mainPanelLayout.createSequentialGroup()
                            .addComponent(panelCoop, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addComponent(panelHumanPlayer, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addComponent(panelTeamInfo, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(panelRobotConfig, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addComponent(labelTitle))
                .addGap(0, 0, Short.MAX_VALUE))
        );
        mainPanelLayout.setVerticalGroup(
            mainPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(mainPanelLayout.createSequentialGroup()
                .addComponent(labelTitle)
                .addGap(23, 23, 23)
                .addComponent(panelTeamInfo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(panelRobotConfig, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(panelScoring, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(mainPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(panelCoop, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(panelHumanPlayer, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(0, 0, Short.MAX_VALUE))
        );

        mainPanelScroll.setViewportView(mainPanel);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(mainPanelScroll)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(mainPanelScroll, javax.swing.GroupLayout.PREFERRED_SIZE, 588, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );

        mainPanelScroll.getVerticalScrollBar().setUnitIncrement(SCROLL_SPEED);

        getAccessibleContext().setAccessibleDescription("");

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void spinnerNumTotesPropertyChange(java.beans.PropertyChangeEvent evt)//GEN-FIRST:event_spinnerNumTotesPropertyChange
    {//GEN-HEADEREND:event_spinnerNumTotesPropertyChange

    }//GEN-LAST:event_spinnerNumTotesPropertyChange

    private void spinnerNumTotesStateChanged(javax.swing.event.ChangeEvent evt)//GEN-FIRST:event_spinnerNumTotesStateChanged
    {//GEN-HEADEREND:event_spinnerNumTotesStateChanged

    }//GEN-LAST:event_spinnerNumTotesStateChanged

    private void spinnerNumContainersStateChanged(javax.swing.event.ChangeEvent evt)//GEN-FIRST:event_spinnerNumContainersStateChanged
    {//GEN-HEADEREND:event_spinnerNumContainersStateChanged

    }//GEN-LAST:event_spinnerNumContainersStateChanged

    private void fieldTeamNumKeyReleased(java.awt.event.KeyEvent evt)//GEN-FIRST:event_fieldTeamNumKeyReleased
    {//GEN-HEADEREND:event_fieldTeamNumKeyReleased

    }//GEN-LAST:event_fieldTeamNumKeyReleased

    private void spinnerMaxNumTotesStateChanged(javax.swing.event.ChangeEvent evt)//GEN-FIRST:event_spinnerMaxNumTotesStateChanged
    {//GEN-HEADEREND:event_spinnerMaxNumTotesStateChanged

    }//GEN-LAST:event_spinnerMaxNumTotesStateChanged

    private void spinnerMaxNumTotesPropertyChange(java.beans.PropertyChangeEvent evt)//GEN-FIRST:event_spinnerMaxNumTotesPropertyChange
    {//GEN-HEADEREND:event_spinnerMaxNumTotesPropertyChange

    }//GEN-LAST:event_spinnerMaxNumTotesPropertyChange

    private void spinnerMaxHeightContainerStateChanged(javax.swing.event.ChangeEvent evt)//GEN-FIRST:event_spinnerMaxHeightContainerStateChanged
    {//GEN-HEADEREND:event_spinnerMaxHeightContainerStateChanged

    }//GEN-LAST:event_spinnerMaxHeightContainerStateChanged

    private void spinnerMaxHeightContainerPropertyChange(java.beans.PropertyChangeEvent evt)//GEN-FIRST:event_spinnerMaxHeightContainerPropertyChange
    {//GEN-HEADEREND:event_spinnerMaxHeightContainerPropertyChange

    }//GEN-LAST:event_spinnerMaxHeightContainerPropertyChange

    private void spinnerMaxCoopStackStateChanged(javax.swing.event.ChangeEvent evt)//GEN-FIRST:event_spinnerMaxCoopStackStateChanged
    {//GEN-HEADEREND:event_spinnerMaxCoopStackStateChanged

    }//GEN-LAST:event_spinnerMaxCoopStackStateChanged

    private void spinnerMaxCoopStackPropertyChange(java.beans.PropertyChangeEvent evt)//GEN-FIRST:event_spinnerMaxCoopStackPropertyChange
    {//GEN-HEADEREND:event_spinnerMaxCoopStackPropertyChange

    }//GEN-LAST:event_spinnerMaxCoopStackPropertyChange

    private void checkBoxTeleopTotesCanPickupActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_checkBoxTeleopTotesCanPickupActionPerformed
    {//GEN-HEADEREND:event_checkBoxTeleopTotesCanPickupActionPerformed

    }//GEN-LAST:event_checkBoxTeleopTotesCanPickupActionPerformed

    private void checkBoxTeleopContainersPickupActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_checkBoxTeleopContainersPickupActionPerformed
    {//GEN-HEADEREND:event_checkBoxTeleopContainersPickupActionPerformed

    }//GEN-LAST:event_checkBoxTeleopContainersPickupActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.ButtonGroup buttonGroupCoop;
    private javax.swing.ButtonGroup buttonGroupHumanPlayer;
    private javax.swing.JCheckBox checkBoxHPThrewNoodles;
    private javax.swing.JCheckBox checkBoxInAutoZone;
    private javax.swing.JCheckBox checkBoxTeleopContainersPickup;
    private javax.swing.JCheckBox checkBoxTeleopContainersUpright;
    private javax.swing.JCheckBox checkBoxTeleopLitterPickupLitter;
    private javax.swing.JCheckBox checkBoxTeleopLitterPushLitter;
    private javax.swing.JCheckBox checkBoxTeleopTotesCanPickup;
    private javax.swing.JCheckBox checkBoxTotesStacked;
    private javax.swing.JTextField fieldScouter;
    private javax.swing.JTextField fieldTeamNum;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JLabel labeCoopMaxStackHeight;
    private javax.swing.JLabel labelFeedLocation;
    private javax.swing.JLabel labelNumContainers;
    private javax.swing.JLabel labelNumTotes;
    private javax.swing.JLabel labelPickupOrintation;
    private javax.swing.JLabel labelScouter;
    private javax.swing.JLabel labelTeamNum;
    private javax.swing.JLabel labelTeleopContainersMaxHeight;
    private javax.swing.JLabel labelTeleopTotesMaxHeight;
    private javax.swing.JLabel labelTitle;
    private javax.swing.JPanel mainPanel;
    private javax.swing.JScrollPane mainPanelScroll;
    private javax.swing.JPanel panelAutoScoredContainer;
    private javax.swing.JPanel panelAutoScoredRobot;
    private javax.swing.JPanel panelAutoScoredTotes;
    private javax.swing.JPanel panelAutonomous;
    private javax.swing.JPanel panelCoop;
    private javax.swing.JPanel panelHumanPlayer;
    private javax.swing.JPanel panelRobotActivityComments;
    private javax.swing.JPanel panelRobotConfig;
    private javax.swing.JPanel panelRobotDriveTrain;
    private javax.swing.JPanel panelRobotShape;
    private javax.swing.JPanel panelRobotWheelType;
    private javax.swing.JPanel panelScoring;
    private javax.swing.JPanel panelTeamInfo;
    private javax.swing.JPanel panelTeleop;
    private javax.swing.JPanel panelTeleopContainer;
    private javax.swing.JPanel panelTeleopLitter;
    private javax.swing.JPanel panelTeleopTotes;
    private javax.swing.JRadioButton radioCoopNone;
    private javax.swing.JRadioButton radioCoopStacked;
    private javax.swing.JRadioButton radioCoopUnstacked;
    private javax.swing.JRadioButton radioHPExcellent;
    private javax.swing.JRadioButton radioHPMediocre;
    private javax.swing.JRadioButton radioHPPoor;
    private javax.swing.JSpinner spinnerMaxCoopStack;
    private javax.swing.JSpinner spinnerMaxHeightContainer;
    private javax.swing.JSpinner spinnerMaxNumTotes;
    private javax.swing.JSpinner spinnerNumContainers;
    private javax.swing.JSpinner spinnerNumTotes;
    private javax.swing.JTextArea textAreaRobotConfigComments;
    private javax.swing.JTextField textBoxRobotDriveTrain;
    private javax.swing.JTextField textBoxRobotShape;
    private javax.swing.JTextField textBoxRobotWheelType;
    private javax.swing.JTextField textBoxTeleopToteFeedLocation;
    private javax.swing.JTextField textBoxTeleopTotePickupOrientation;
    // End of variables declaration//GEN-END:variables
}
