package com;

public class Main {
    public static void main(String[] args) {
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Windows".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Merise.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        new Merise();
//        System.out.println( "   massi.merise.merise.merise.merise.merise.merise.merise ".trim().replaceAll("(.merise)+",".merise"));
    }
}
