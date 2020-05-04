package ui;

import com.googlecode.lanterna.Symbols;
import com.googlecode.lanterna.TextCharacter;
import com.googlecode.lanterna.TextColor;
import com.googlecode.lanterna.terminal.DefaultTerminalFactory;
import com.googlecode.lanterna.terminal.Terminal;
import strac.align.interpreter.MonitoringService;

import javax.swing.*;
import java.io.IOException;

/**
 * @author Javier Cabrera-Arteaga on 2020-05-03
 */
public class UI {
    public Terminal screen;
    private TextColor foreC, backC, header;

    public void init(){
        try {
            screen = new DefaultTerminalFactory().createTerminal();
            //screen = t.createScreen();
            screen.resetColorAndSGR();
            screen.clearScreen();
            foreC = TextColor.ANSI.RED;
            backC = TextColor.ANSI.DEFAULT;
            header = TextColor.ANSI.BLACK;

        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    private int padJobName(MonitoringService.JobInfo[] infos){
        try {
            if (infos == null || infos.length == 0)
                return 0;

            int max = infos[0].name.length();

            for (int i = 0; i < infos.length; i++)
                if (infos[i].name.length() > max)
                    max = infos[i].name.length();

            return max;
        }
        catch (Exception e){
            return 0;
        }
    }

    MonitoringService.JobInfo[] infos = new MonitoringService.JobInfo[0];
    int overall=0;
    String footer="";

    public void setInfos(MonitoringService.JobInfo[] infos){
        this.infos = infos;
        try {
            this.drawUI();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void setOverall(int overall){
        this.overall = overall;
        try {
            this.drawUI();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void setFooter(String footer){
        this.footer = footer;
        try {
            this.drawUI();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void drawUI() throws IOException {

        screen.setCursorPosition(0,0);

        int width = screen.getTerminalSize().getColumns() - 1;
        int height = screen.getTerminalSize().getRows() - 1;
        int pad = 1;
        int marginTop = 2;

        int size = padJobName(infos);

        int rightProgressPad = 5;

        //screen.clearScreen();

        screen.newTextGraphics().drawLine(0, 0, width, 0,
                new TextCharacter(' ').withBackgroundColor(foreC).withForegroundColor(backC));

        screen.newTextGraphics().setBackgroundColor(foreC).setForegroundColor(header).putCSIStyledString(pad, 0 , "STRAC is aligning");

        screen.newTextGraphics().drawLine(0, height, width, height, new TextCharacter(' ')
                .withBackgroundColor(foreC).withForegroundColor(backC));
        screen.newTextGraphics().setBackgroundColor(foreC).setForegroundColor(header)
                .putCSIStyledString(pad, height, footer);

        int pBarPad = 4;

        screen.newTextGraphics().drawLine(
                footer.length() + pBarPad + 1, height,
                footer.length() + pBarPad + overall + 1, height, new TextCharacter(' ').withBackgroundColor(header).withForegroundColor(backC));

        screen.newTextGraphics().drawLine(
                footer.length() + pBarPad + overall + 1, height,
                footer.length() + pBarPad + 100 + 1, height, new TextCharacter('.').withBackgroundColor(foreC).withForegroundColor(header));

        screen.newTextGraphics().setBackgroundColor(foreC).setForegroundColor(header)
                .putCSIStyledString(footer.length() + pBarPad + 101, height, String.format(" %s%%", overall));

        try {

            for (int i = 0; i < infos.length; i++) {

                int left = 5 * pad + size;
                int top = marginTop + 2 * i;

                String percent = String.format("%03d%% ETA: %s", 100 * infos[i].progress / infos[i].total, infos[i].eta);
                int max = width - left - pad - percent.length() - 5 * pad - rightProgressPad;

                int upto = max * infos[i].progress / infos[i].total;


                screen.newTextGraphics().setBackgroundColor(backC).setForegroundColor(foreC)
                        .putCSIStyledString(pad, top, infos[i].name);

                screen.newTextGraphics().setBackgroundColor(backC).setForegroundColor(foreC)
                        .putCSIStyledString(left - 1, top, "[");

                screen.newTextGraphics().drawLine(
                        left, top,
                        upto + left, top, new TextCharacter(Symbols.BLOCK_SOLID).withBackgroundColor(backC).withForegroundColor(foreC));

                screen.newTextGraphics().drawLine(
                        left + upto, top,
                        max + left, top, new TextCharacter('.').withBackgroundColor(backC).withForegroundColor(foreC));

                screen.newTextGraphics().setBackgroundColor(backC).setForegroundColor(foreC)
                        .putCSIStyledString(max + left + pad * 3, top, percent);

                screen.newTextGraphics().setBackgroundColor(backC).setForegroundColor(foreC)
                        .putCSIStyledString(max + left, top, "]");
            }


        }
        catch (Exception ignored){

        }
        //screen.newTextGraphics().setBackgroundColor(foreC).setForegroundColor(backC).putCSIStyledString(0, 0, header);

        //screen.setCursorPosition(width,height);


        screen.flush();
    }

    public void clear() throws IOException {
        screen.resetColorAndSGR();
        screen.clearScreen();
    }
}
