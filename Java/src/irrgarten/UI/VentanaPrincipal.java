package irrgarten.UI;


/**
 *
 * @author ${user}
 */
public class VentanaPrincipal extends javax.swing.JFrame {

   
    public VentanaPrincipal() {
        initComponents();
        this.setSize(1000,1000);
        setVisible(true);
    }

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    // <editor-fold defaultstate="collapsed" desc=" Generated Code ">                          
    private void initComponents() {

        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosing(java.awt.event.WindowEvent evt) {
                exitForm(evt);
            }
        });

        pack();
    }
    // </editor-fold>                        

    /** Exit the Application */
    private void exitForm(java.awt.event.WindowEvent evt) {                          
        System.exit(0);
    }                         


    // Variables declaration - do not modify                     
    // End of variables declaration                   

}
