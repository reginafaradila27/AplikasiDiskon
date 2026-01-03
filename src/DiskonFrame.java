import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class DiskonFrame extends JFrame {

    JTextField txtHarga, txtKupon;
    JComboBox<String> cmbDiskon;
    JSlider sldDiskon;
    JLabel lblHasil, lblHemat;
    JTextArea txtRiwayat;
    JButton btnHitung;

    public DiskonFrame() {
        setTitle("Aplikasi Perhitungan Diskon");
        setSize(500, 550);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // ===== PANEL INPUT =====
        JPanel panelInput = new JPanel(new GridLayout(6, 2, 5, 5));

        panelInput.add(new JLabel("Harga Asli"));
        txtHarga = new JTextField();
        panelInput.add(txtHarga);

        panelInput.add(new JLabel("Diskon (%)"));
        cmbDiskon = new JComboBox<>(new String[]{"10%", "20%", "30%", "40%", "50%"});
        panelInput.add(cmbDiskon);

        panelInput.add(new JLabel("Diskon Slider"));
        sldDiskon = new JSlider(0, 50, 10);
        sldDiskon.setMajorTickSpacing(10);
        sldDiskon.setPaintTicks(true);
        sldDiskon.setPaintLabels(true);
        panelInput.add(sldDiskon);

        panelInput.add(new JLabel("Kode Kupon"));
        txtKupon = new JTextField();
        panelInput.add(txtKupon);

        btnHitung = new JButton("Hitung");
        panelInput.add(btnHitung);

        lblHasil = new JLabel("Harga Akhir: Rp 0");
        panelInput.add(lblHasil);

        add(panelInput, BorderLayout.NORTH);

        // ===== PANEL HASIL =====
        JPanel panelHasil = new JPanel(new GridLayout(1, 1));
        lblHemat = new JLabel("Penghematan: Rp 0");
        panelHasil.add(lblHemat);
        add(panelHasil, BorderLayout.CENTER);

        // ===== PANEL RIWAYAT =====
        txtRiwayat = new JTextArea();
        txtRiwayat.setEditable(false);
        JScrollPane scroll = new JScrollPane(txtRiwayat);
        add(scroll, BorderLayout.SOUTH);

        // ===== EVENT =====
        btnHitung.addActionListener(e -> hitungDiskon());

        cmbDiskon.addItemListener(e -> {
            if (e.getStateChange() == ItemEvent.SELECTED) {
                int diskon = Integer.parseInt(
                        cmbDiskon.getSelectedItem().toString().replace("%", "")
                );
                sldDiskon.setValue(diskon);
            }
        });
    }

    void hitungDiskon() {
        try {
            double harga = Double.parseDouble(txtHarga.getText());

            int diskonCombo = Integer.parseInt(
                    cmbDiskon.getSelectedItem().toString().replace("%", "")
            );
            int diskonSlider = sldDiskon.getValue();
            int diskon = Math.max(diskonCombo, diskonSlider);

            String kupon = txtKupon.getText().toUpperCase();
            if (kupon.equals("HEMAT10")) {
                diskon += 10;
            } else if (kupon.equals("SAVE5")) {
                diskon += 5;
            }

            if (diskon > 100) diskon = 100;

            double potongan = harga * diskon / 100;
            double hargaAkhir = harga - potongan;

            lblHasil.setText("Harga Akhir: Rp " + hargaAkhir);
            lblHemat.setText("Penghematan: Rp " + potongan);

            txtRiwayat.append(
                    "Harga: " + harga +
                    " | Diskon: " + diskon + "%" +
                    " | Akhir: " + hargaAkhir + "\n"
            );

        } catch (Exception e) {
            JOptionPane.showMessageDialog(this,
                    "Input harga tidak valid!",
                    "Error",
                    JOptionPane.ERROR_MESSAGE);
        }
    }

    public static void main(String[] args) {
        new DiskonFrame().setVisible(true);
    }
}
