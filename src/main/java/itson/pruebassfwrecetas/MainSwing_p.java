package itson.pruebassfwrecetas;

import javax.swing.*;
import javax.swing.border.*;
import javax.swing.table.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.geom.*;
import java.util.*;
import java.util.List;

/**
 * Aplicación de Recetas de Cocina — Interfaz Swing
 *
 * Atributos de calidad: - Funcionalidad : todas las operaciones del servicio
 * expuestas. - Fiabilidad : validaciones de formulario antes de enviar al
 * servicio. - Mantenibilidad: paneles separados por responsabilidad. -
 * Usabilidad : navegación lateral, feedback visual, confirmaciones.
 */
public class MainSwing_p extends JFrame {

    static final Color BG_DARK = new Color(18, 18, 24);
    static final Color BG_PANEL = new Color(26, 26, 36);
    static final Color BG_CARD = new Color(36, 36, 50);
    static final Color BG_SIDEBAR = new Color(14, 14, 20);
    static final Color ACCENT = new Color(255, 145, 50);
    static final Color ACCENT2 = new Color(255, 195, 100);
    static final Color TEXT_PRI = new Color(240, 240, 245);
    static final Color TEXT_SEC = new Color(150, 150, 170);
    static final Color TEXT_MUT = new Color(90, 90, 110);
    static final Color SUCCESS = new Color(80, 200, 120);
    static final Color DANGER = new Color(220, 80, 80);
    static final Color BORDER_C = new Color(50, 50, 70);
    static final Font FONT_TITLE = new Font("SansSerif", Font.BOLD, 22);
    static final Font FONT_H2 = new Font("SansSerif", Font.BOLD, 16);
    static final Font FONT_H3 = new Font("SansSerif", Font.BOLD, 13);
    static final Font FONT_BODY = new Font("SansSerif", Font.PLAIN, 13);
    static final Font FONT_SMALL = new Font("SansSerif", Font.PLAIN, 11);
    static final Font FONT_MONO = new Font("Monospaced", Font.PLAIN, 12);
    private final RecetaService servicio;
    private final CardLayout cardLayout = new CardLayout();
    private final JPanel mainContent = new JPanel(cardLayout);
    private PanelListado panelListado;
    private PanelDetalle panelDetalle;
    private PanelAgregar panelAgregar;
    private PanelFavoritas panelFavoritas;
    private PanelBusqueda panelBusqueda;
    private PanelCompras panelCompras;

    private final List<JButton> navBtns = new ArrayList<>();

    public MainSwing_p(RecetaService servicio) {
        this.servicio = servicio;
        setTitle("App de recetas — Mis Recetas");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(1100, 720);
        setMinimumSize(new Dimension(900, 600));
        setLocationRelativeTo(null);
        getContentPane().setBackground(BG_DARK);
        setLayout(new BorderLayout());
        add(buildSidebar(), BorderLayout.WEST);
        add(buildMain(), BorderLayout.CENTER);
        navigateTo("listado");
    }

    private JPanel buildSidebar() {
        JPanel sidebar = new JPanel();
        sidebar.setBackground(BG_SIDEBAR);
        sidebar.setPreferredSize(new Dimension(210, 0));
        sidebar.setLayout(new BoxLayout(sidebar, BoxLayout.Y_AXIS));
        sidebar.setBorder(BorderFactory.createMatteBorder(0, 0, 0, 1, BORDER_C));
        JPanel logo = new JPanel(new FlowLayout(FlowLayout.LEFT, 18, 0));
        logo.setBackground(BG_SIDEBAR);
        logo.setMaximumSize(new Dimension(210, 70));
        logo.setPreferredSize(new Dimension(210, 70));
        JLabel icon = new JLabel("🍽");
        icon.setFont(new Font("SansSerif", Font.PLAIN, 28));
        JLabel name = new JLabel("App de recetas");
        name.setFont(new Font("SansSerif", Font.BOLD, 17));
        name.setForeground(ACCENT);
        logo.add(icon);
        logo.add(name);
        sidebar.add(logo);
        sidebar.add(separator());
        addNav(sidebar, "listado", "📋", "Todas las recetas");
        addNav(sidebar, "busqueda", "🔍", "Buscar");
        addNav(sidebar, "favoritas", "⭐", "Favoritas");
        addNav(sidebar, "agregar", "➕", "Nueva receta");
        addNav(sidebar, "compras", "🛒", "Lista de compras");
        sidebar.add(Box.createVerticalGlue());
        sidebar.add(separator());
        JLabel version = new JLabel("Equipo 4 - Pruebas de software");
        version.setFont(FONT_SMALL);
        version.setForeground(TEXT_MUT);
        version.setBorder(new EmptyBorder(10, 18, 10, 0));
        sidebar.add(version);
        return sidebar;
    }

    private void addNav(JPanel parent, String key, String emoji, String label) {
        JButton btn = new JButton(emoji + "  " + label) {
            boolean active = false;

            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                if (active) {
                    g2.setColor(new Color(255, 145, 50, 40));
                    g2.fillRoundRect(8, 4, getWidth() - 16, getHeight() - 8, 10, 10);
                    g2.setColor(ACCENT);
                    g2.fillRoundRect(0, 6, 3, getHeight() - 12, 3, 3);
                } else if (getModel().isRollover()) {
                    g2.setColor(new Color(255, 255, 255, 12));
                    g2.fillRoundRect(8, 4, getWidth() - 16, getHeight() - 8, 10, 10);
                }
                g2.dispose();
                super.paintComponent(g);
            }

            public void setActive(boolean a) {
                active = a;
                setForeground(a ? ACCENT : TEXT_SEC);
                repaint();
            }
        };
        btn.setFont(FONT_BODY);
        btn.setForeground(TEXT_SEC);
        btn.setBackground(new Color(0, 0, 0, 0));
        btn.setOpaque(false);
        btn.setContentAreaFilled(false);
        btn.setBorderPainted(false);
        btn.setFocusPainted(false);
        btn.setHorizontalAlignment(SwingConstants.LEFT);
        btn.setBorder(new EmptyBorder(8, 16, 8, 16));
        btn.setMaximumSize(new Dimension(210, 46));
        btn.setPreferredSize(new Dimension(210, 46));
        btn.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        btn.addActionListener(e -> navigateTo(key));
        navBtns.add(btn);
        btn.putClientProperty("key", key);
        parent.add(btn);
    }

    private Component separator() {
        JSeparator sep = new JSeparator();
        sep.setForeground(BORDER_C);
        sep.setBackground(BORDER_C);
        sep.setMaximumSize(new Dimension(210, 1));
        return sep;
    }

    private JPanel buildMain() {
        mainContent.setBackground(BG_DARK);
        panelListado = new PanelListado();
        panelDetalle = new PanelDetalle();
        panelAgregar = new PanelAgregar();
        panelFavoritas = new PanelFavoritas();
        panelBusqueda = new PanelBusqueda();
        panelCompras = new PanelCompras();

        mainContent.add(panelListado, "listado");
        mainContent.add(panelDetalle, "detalle");
        mainContent.add(panelAgregar, "agregar");
        mainContent.add(panelFavoritas, "favoritas");
        mainContent.add(panelBusqueda, "busqueda");
        mainContent.add(panelCompras, "compras");
        return mainContent;
    }

    private void navigateTo(String key) {
        cardLayout.show(mainContent, key);
        for (JButton b : navBtns) {
            Object k = b.getClientProperty("key");
            try {
                b.getClass().getMethod("setActive", boolean.class).invoke(b, key.equals(k));
            } catch (Exception ignored) {
            }
        }
        switch (key) {
            case "listado" ->
                panelListado.refresh();
            case "favoritas" ->
                panelFavoritas.refresh();
            case "busqueda" -> {
            }
        }
    }

    static JLabel label(String text, Font f, Color c) {
        JLabel l = new JLabel(text);
        l.setFont(f);
        l.setForeground(c);
        return l;
    }

    static JButton accentBtn(String text) {
        JButton btn = new JButton(text) {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                Color base = getModel().isPressed() ? ACCENT.darker() : getModel().isRollover() ? ACCENT.brighter() : ACCENT;
                g2.setColor(base);
                g2.fillRoundRect(0, 0, getWidth(), getHeight(), 10, 10);
                g2.dispose();
                super.paintComponent(g);
            }
        };
        btn.setFont(FONT_H3);
        btn.setForeground(Color.WHITE);
        btn.setOpaque(false);
        btn.setContentAreaFilled(false);
        btn.setBorderPainted(false);
        btn.setFocusPainted(false);
        btn.setBorder(new EmptyBorder(8, 20, 8, 20));
        btn.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        return btn;
    }

    static JButton ghostBtn(String text, Color col) {
        JButton btn = new JButton(text) {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                if (getModel().isRollover()) {
                    g2.setColor(new Color(col.getRed(), col.getGreen(), col.getBlue(), 30));
                    g2.fillRoundRect(0, 0, getWidth(), getHeight(), 10, 10);
                }
                g2.setColor(col);
                g2.setStroke(new BasicStroke(1.5f));
                g2.drawRoundRect(1, 1, getWidth() - 2, getHeight() - 2, 10, 10);
                g2.dispose();
                super.paintComponent(g);
            }
        };
        btn.setFont(FONT_H3);
        btn.setForeground(col);
        btn.setOpaque(false);
        btn.setContentAreaFilled(false);
        btn.setBorderPainted(false);
        btn.setFocusPainted(false);
        btn.setBorder(new EmptyBorder(7, 18, 7, 18));
        btn.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        return btn;
    }

    static JTextField darkField(String placeholder) {
        JTextField tf = new JTextField(placeholder) {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setColor(BG_CARD);
                g2.fillRoundRect(0, 0, getWidth(), getHeight(), 8, 8);
                if (isFocusOwner()) {
                    g2.setColor(ACCENT);
                    g2.setStroke(new BasicStroke(1.5f));
                    g2.drawRoundRect(1, 1, getWidth() - 2, getHeight() - 2, 8, 8);
                }
                g2.dispose();
                super.paintComponent(g);
            }
        };
        tf.setFont(FONT_BODY);
        tf.setForeground(TEXT_PRI);
        tf.setCaretColor(ACCENT);
        tf.setBackground(BG_CARD);
        tf.setOpaque(false);
        tf.setBorder(new EmptyBorder(8, 12, 8, 12));
        tf.setPreferredSize(new Dimension(0, 38));
        return tf;
    }

    static JTextArea darkArea() {
        JTextArea ta = new JTextArea();
        ta.setFont(FONT_BODY);
        ta.setForeground(TEXT_PRI);
        ta.setBackground(BG_CARD);
        ta.setCaretColor(ACCENT);
        ta.setBorder(new EmptyBorder(8, 12, 8, 12));
        ta.setLineWrap(true);
        ta.setWrapStyleWord(true);
        return ta;
    }

    static JScrollPane darkScroll(Component c) {
        JScrollPane sp = new JScrollPane(c);
        sp.setBackground(BG_DARK);
        sp.getViewport().setBackground(BG_DARK);
        sp.setBorder(null);
        sp.getVerticalScrollBar().setUI(new javax.swing.plaf.basic.BasicScrollBarUI() {
            @Override
            protected void configureScrollBarColors() {
                thumbColor = new Color(80, 80, 100);
                trackColor = BG_PANEL;
            }

            @Override
            protected JButton createDecreaseButton(int o) {
                return zeroBtn();
            }

            @Override
            protected JButton createIncreaseButton(int o) {
                return zeroBtn();
            }

            JButton zeroBtn() {
                JButton b = new JButton();
                b.setPreferredSize(new Dimension(0, 0));
                return b;
            }
        });
        sp.getVerticalScrollBar().setPreferredSize(new Dimension(6, 0));
        return sp;
    }

    static JPanel card(int arc) {
        return new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setColor(BG_CARD);
                g2.fillRoundRect(0, 0, getWidth(), getHeight(), arc, arc);
                g2.setColor(BORDER_C);
                g2.setStroke(new BasicStroke(1f));
                g2.drawRoundRect(0, 0, getWidth() - 1, getHeight() - 1, arc, arc);
                g2.dispose();
                super.paintComponent(g);
            }
        };
    }

    static JPanel pageHeader(String title, String subtitle) {
        JPanel h = new JPanel(new BorderLayout(0, 4));
        h.setOpaque(false);
        h.setBorder(new EmptyBorder(28, 32, 20, 32));
        JLabel t = label(title, FONT_TITLE, TEXT_PRI);
        JLabel s = label(subtitle, FONT_BODY, TEXT_SEC);
        h.add(t, BorderLayout.NORTH);
        h.add(s, BorderLayout.CENTER);
        return h;
    }

    class PanelListado extends JPanel {

        JPanel grid;

        PanelListado() {
            setLayout(new BorderLayout());
            setBackground(BG_DARK);

            JPanel header = pageHeader("📋 Todas las recetas", "Explora y gestiona tu colección completa");
            add(header, BorderLayout.NORTH);

            grid = new JPanel(new GridLayout(0, 2, 16, 16));
            grid.setBackground(BG_DARK);
            grid.setBorder(new EmptyBorder(0, 32, 32, 32));

            add(darkScroll(grid), BorderLayout.CENTER);
        }

        void refresh() {
            grid.removeAll();
            List<Receta> lista = servicio.obtenerTodasLasRecetas();
            if (lista.isEmpty()) {
                JLabel empty = label("No hay recetas aún. ¡Agrega la primera!", FONT_H3, TEXT_SEC);
                empty.setHorizontalAlignment(SwingConstants.CENTER);
                grid.add(empty);
            } else {
                for (Receta r : lista) {
                    grid.add(buildRecetaCard(r));
                }
            }
            grid.revalidate();
            grid.repaint();
        }

        JPanel buildRecetaCard(Receta r) {
            JPanel card = card(14);
            card.setOpaque(false);
            card.setLayout(new BorderLayout(0, 0));
            card.setBorder(new EmptyBorder(18, 20, 18, 20));
            card.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

            // Top row
            JPanel top = new JPanel(new BorderLayout());
            top.setOpaque(false);
            JLabel nombre = label(r.getNombre(), FONT_H2, TEXT_PRI);
            JLabel fav = label(r.isFavorita() ? "⭐" : "☆", new Font("SansSerif", Font.PLAIN, 18), r.isFavorita() ? ACCENT2 : TEXT_MUT);
            fav.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
            fav.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    servicio.toggleFavorita(r.getId());
                    refresh();
                }
            });
            top.add(nombre, BorderLayout.CENTER);
            top.add(fav, BorderLayout.EAST);
            JPanel tags = new JPanel(new FlowLayout(FlowLayout.LEFT, 6, 6));
            tags.setOpaque(false);
            tags.add(buildTag("🍴 " + r.getTipoCocina(), ACCENT));
            tags.add(buildTag("⏱ " + r.getTiempoPreparacion() + " min", SUCCESS));
            tags.add(buildTag(r.getIngredientes().size() + " ingredientes", TEXT_SEC));
            JPanel btns = new JPanel(new FlowLayout(FlowLayout.LEFT, 8, 0));
            btns.setOpaque(false);
            JButton ver = accentBtn("Ver receta");
            JButton del = ghostBtn("Eliminar", DANGER);
            ver.setFont(FONT_SMALL);
            del.setFont(FONT_SMALL);
            ver.setBorder(new EmptyBorder(5, 14, 5, 14));
            del.setBorder(new EmptyBorder(5, 14, 5, 14));
            ver.addActionListener(e -> showDetalle(r.getId()));
            del.addActionListener(e -> {
                int resp = JOptionPane.showConfirmDialog(MainSwing_p.this,
                        "¿Eliminar la receta \"" + r.getNombre() + "\"?",
                        "Confirmar eliminación", JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);
                if (resp == JOptionPane.YES_OPTION) {
                    servicio.eliminarReceta(r.getId());
                    refresh();
                }
            });
            btns.add(ver);
            btns.add(del);
            card.add(top, BorderLayout.NORTH);
            card.add(tags, BorderLayout.CENTER);
            card.add(btns, BorderLayout.SOUTH);
            card.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseEntered(MouseEvent e) {
                    card.setBorder(new CompoundBorder(new LineBorder(new Color(ACCENT.getRed(), ACCENT.getGreen(), ACCENT.getBlue(), 100), 1, true), new EmptyBorder(17, 19, 17, 19)));
                }

                @Override
                public void mouseExited(MouseEvent e) {
                    card.setBorder(new EmptyBorder(18, 20, 18, 20));
                }
            });
            return card;
        }

        JLabel buildTag(String text, Color col) {
            JLabel lbl = new JLabel(text) {
                @Override
                protected void paintComponent(Graphics g) {
                    Graphics2D g2 = (Graphics2D) g.create();
                    g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                    g2.setColor(new Color(col.getRed(), col.getGreen(), col.getBlue(), 30));
                    g2.fillRoundRect(0, 0, getWidth(), getHeight(), 20, 20);
                    g2.dispose();
                    super.paintComponent(g);
                }
            };
            lbl.setFont(FONT_SMALL);
            lbl.setForeground(col);
            lbl.setBorder(new EmptyBorder(3, 10, 3, 10));
            lbl.setOpaque(false);
            return lbl;
        }
    }

    class PanelDetalle extends JPanel {

        JLabel lblNombre = label("", FONT_TITLE, TEXT_PRI);
        JLabel lblTipo = label("", FONT_BODY, TEXT_SEC);
        JLabel lblTiempo = label("", FONT_BODY, ACCENT);
        JPanel ingredGrid = new JPanel();
        JPanel pasosPanel = new JPanel();

        PanelDetalle() {
            setLayout(new BorderLayout());
            setBackground(BG_DARK);
        }

        void load(int id) {
            servicio.buscarPorId(id).ifPresent(r -> {
                removeAll();
                JPanel header = new JPanel(new BorderLayout(0, 8));
                header.setOpaque(false);
                header.setBorder(new EmptyBorder(28, 32, 16, 32));
                JButton back = ghostBtn("← Volver", TEXT_SEC);
                back.setFont(FONT_SMALL);
                back.setBorder(new EmptyBorder(5, 0, 5, 5));
                back.addActionListener(e -> navigateTo("listado"));
                header.add(back, BorderLayout.NORTH);
                lblNombre.setText(r.isFavorita() ? "⭐ " + r.getNombre() : r.getNombre());
                lblTipo.setText("Cocina: " + r.getTipoCocina());
                lblTiempo.setText("⏱ " + r.getTiempoPreparacion() + " minutos de preparación");
                JPanel info = new JPanel(new GridLayout(3, 1, 0, 4));
                info.setOpaque(false);
                info.add(lblNombre);
                info.add(lblTipo);
                info.add(lblTiempo);
                header.add(info, BorderLayout.CENTER);
                JPanel actionRow = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 0));
                actionRow.setOpaque(false);
                JButton favBtn = r.isFavorita() ? ghostBtn("☆ Quitar favorita", ACCENT2) : accentBtn("⭐ Añadir a favoritas");
                favBtn.addActionListener(e -> {
                    servicio.toggleFavorita(id);
                    load(id);
                });
                actionRow.add(favBtn);
                header.add(actionRow, BorderLayout.SOUTH);
                add(header, BorderLayout.NORTH);
                JPanel body = new JPanel(new GridLayout(1, 2, 20, 0));
                body.setOpaque(false);
                body.setBorder(new EmptyBorder(0, 32, 32, 32));
                JPanel ingCard = card(14);
                ingCard.setOpaque(false);
                ingCard.setLayout(new BorderLayout(0, 10));
                ingCard.setBorder(new EmptyBorder(20, 20, 20, 20));
                ingCard.add(label("🧂 Ingredientes", FONT_H2, TEXT_PRI), BorderLayout.NORTH);
                JPanel ingList = new JPanel();
                ingList.setLayout(new BoxLayout(ingList, BoxLayout.Y_AXIS));
                ingList.setOpaque(false);
                for (Ingrediente ing : r.getIngredientes()) {
                    JPanel row = new JPanel(new BorderLayout());
                    row.setOpaque(false);
                    row.setBorder(new EmptyBorder(6, 0, 6, 0));
                    JLabel nameL = label("• " + ing.getNombre(), FONT_BODY, TEXT_PRI);
                    JLabel qty = label(String.format("%.1f %s", ing.getCantidad(), ing.getUnidad()), FONT_BODY, ACCENT);
                    qty.setHorizontalAlignment(SwingConstants.RIGHT);
                    row.add(nameL, BorderLayout.CENTER);
                    row.add(qty, BorderLayout.EAST);
                    JSeparator sep = new JSeparator();
                    sep.setForeground(BORDER_C);
                    JPanel wrap = new JPanel(new BorderLayout());
                    wrap.setOpaque(false);
                    wrap.add(row, BorderLayout.CENTER);
                    wrap.add(sep, BorderLayout.SOUTH);
                    ingList.add(wrap);
                }
                ingCard.add(darkScroll(ingList), BorderLayout.CENTER);
                JPanel pasosCard = card(14);
                pasosCard.setOpaque(false);
                pasosCard.setLayout(new BorderLayout(0, 10));
                pasosCard.setBorder(new EmptyBorder(20, 20, 20, 20));
                pasosCard.add(label("👨‍🍳 Instrucciones", FONT_H2, TEXT_PRI), BorderLayout.NORTH);
                JPanel pasosList = new JPanel();
                pasosList.setLayout(new BoxLayout(pasosList, BoxLayout.Y_AXIS));
                pasosList.setOpaque(false);
                List<String> pasos = servicio.obtenerInstruccionesPasoAPaso(id);
                for (String paso : pasos) {
                    JPanel row = new JPanel(new BorderLayout(12, 0));
                    row.setOpaque(false);
                    row.setBorder(new EmptyBorder(8, 0, 8, 0));
                    // Número del paso
                    String num = paso.substring(0, paso.indexOf(":") + 1);
                    String txt = paso.substring(paso.indexOf(":") + 1).trim();
                    JLabel numL = label(num, FONT_H3, ACCENT);
                    numL.setPreferredSize(new Dimension(55, 0));
                    numL.setVerticalAlignment(SwingConstants.TOP);
                    JTextArea ta = new JTextArea(txt);
                    ta.setFont(FONT_BODY);
                    ta.setForeground(TEXT_PRI);
                    ta.setBackground(new Color(0, 0, 0, 0));
                    ta.setOpaque(false);
                    ta.setEditable(false);
                    ta.setLineWrap(true);
                    ta.setWrapStyleWord(true);
                    ta.setBorder(null);
                    row.add(numL, BorderLayout.WEST);
                    row.add(ta, BorderLayout.CENTER);
                    JSeparator sep = new JSeparator();
                    sep.setForeground(BORDER_C);
                    JPanel wrap = new JPanel(new BorderLayout());
                    wrap.setOpaque(false);
                    wrap.add(row, BorderLayout.CENTER);
                    wrap.add(sep, BorderLayout.SOUTH);
                    pasosList.add(wrap);
                }
                pasosCard.add(darkScroll(pasosList), BorderLayout.CENTER);

                body.add(ingCard);
                body.add(pasosCard);
                add(body, BorderLayout.CENTER);
                revalidate();
                repaint();
            });
        }
    }

    private void showDetalle(int id) {
        panelDetalle.load(id);
        cardLayout.show(mainContent, "detalle");
    }

    class PanelAgregar extends JPanel {

        JTextField fNombre = darkField("");
        JTextField fTipo = darkField("");
        JTextField fTiempo = darkField("");
        JTextArea fPasos = darkArea();
        DefaultTableModel ingModel = new DefaultTableModel(new String[]{"Ingrediente", "Cantidad", "Unidad"}, 0);
        JTable ingTable;

        PanelAgregar() {
            setLayout(new BorderLayout());
            setBackground(BG_DARK);
            add(pageHeader("➕ Nueva receta", "Completa los campos para agregar una receta"), BorderLayout.NORTH);
            JPanel form = new JPanel(new GridBagLayout());
            form.setBackground(BG_DARK);
            form.setBorder(new EmptyBorder(0, 32, 32, 32));
            GridBagConstraints gc = new GridBagConstraints();
            gc.fill = GridBagConstraints.HORIZONTAL;
            gc.insets = new Insets(4, 0, 4, 12);
            JPanel left = new JPanel(new GridBagLayout());
            left.setBackground(BG_DARK);
            GridBagConstraints lc = new GridBagConstraints();
            lc.fill = GridBagConstraints.HORIZONTAL;
            lc.weightx = 1;
            lc.gridx = 0;
            lc.gridy = 0;
            left.add(label("Nombre de la receta *", FONT_H3, TEXT_SEC), lc);
            lc.gridy = 1;
            left.add(fNombre, lc);
            lc.gridy = 2;
            left.add(Box.createVerticalStrut(8), lc);
            lc.gridy = 3;
            left.add(label("Tipo de cocina *", FONT_H3, TEXT_SEC), lc);
            lc.gridy = 4;
            left.add(fTipo, lc);
            lc.gridy = 5;
            left.add(Box.createVerticalStrut(8), lc);
            lc.gridy = 6;
            left.add(label("Tiempo de preparación (minutos) *", FONT_H3, TEXT_SEC), lc);
            lc.gridy = 7;
            left.add(fTiempo, lc);
            lc.gridy = 8;
            left.add(Box.createVerticalStrut(8), lc);
            lc.gridy = 9;
            left.add(label("Pasos (uno por línea)", FONT_H3, TEXT_SEC), lc);
            lc.gridy = 10;
            lc.weighty = 1;
            lc.fill = GridBagConstraints.BOTH;
            JScrollPane spPasos = darkScroll(fPasos);
            spPasos.setPreferredSize(new Dimension(0, 130));
            spPasos.setBorder(BorderFactory.createLineBorder(BORDER_C, 1));
            left.add(spPasos, lc);
            JPanel right = new JPanel(new BorderLayout(0, 8));
            right.setBackground(BG_DARK);
            right.add(label("Ingredientes", FONT_H3, TEXT_SEC), BorderLayout.NORTH);
            ingTable = new JTable(ingModel) {
                @Override
                public Component prepareRenderer(TableCellRenderer r, int row, int col) {
                    Component c = super.prepareRenderer(r, row, col);
                    c.setBackground(row % 2 == 0 ? BG_CARD : BG_PANEL);
                    c.setForeground(TEXT_PRI);
                    ((JComponent) c).setBorder(new EmptyBorder(4, 8, 4, 8));
                    return c;
                }
            };
            ingTable.setBackground(BG_CARD);
            ingTable.setForeground(TEXT_PRI);
            ingTable.setGridColor(BORDER_C);
            ingTable.setRowHeight(30);
            ingTable.setFont(FONT_BODY);
            ingTable.getTableHeader().setBackground(BG_PANEL);
            ingTable.getTableHeader().setForeground(TEXT_SEC);
            ingTable.getTableHeader().setFont(FONT_H3);
            ingTable.getTableHeader().setBorder(new MatteBorder(0, 0, 1, 0, BORDER_C));
            ingTable.setSelectionBackground(new Color(ACCENT.getRed(), ACCENT.getGreen(), ACCENT.getBlue(), 40));
            JScrollPane spIng = darkScroll(ingTable);
            spIng.setBorder(BorderFactory.createLineBorder(BORDER_C, 1));
            spIng.setPreferredSize(new Dimension(0, 200));
            right.add(spIng, BorderLayout.CENTER);
            JPanel addIngRow = new JPanel(new GridLayout(1, 4, 6, 0));
            addIngRow.setBackground(BG_DARK);
            JTextField iNombre = darkField("Nombre");
            JTextField iCant = darkField("Cant.");
            JTextField iUnidad = darkField("Unidad");
            JButton addIng = accentBtn("+");
            addIng.setFont(FONT_H2);
            addIng.setBorder(new EmptyBorder(6, 12, 6, 12));
            addIng.addActionListener(e -> {
                String n = iNombre.getText().trim();
                String c = iCant.getText().trim();
                String u = iUnidad.getText().trim();
                if (n.isEmpty() || c.isEmpty()) {
                    showError("Nombre y cantidad son obligatorios.");
                    return;
                }
                try {
                    double cant = Double.parseDouble(c.replace(",", "."));
                    ingModel.addRow(new Object[]{n, cant, u});
                    iNombre.setText("");
                    iCant.setText("");
                    iUnidad.setText("");
                } catch (NumberFormatException ex) {
                    showError("La cantidad debe ser un número.");
                }
            });
            addIngRow.add(iNombre);
            addIngRow.add(iCant);
            addIngRow.add(iUnidad);
            addIngRow.add(addIng);

            JButton delIng = ghostBtn("Quitar seleccionado", DANGER);
            delIng.setFont(FONT_SMALL);
            delIng.addActionListener(e -> {
                int sel = ingTable.getSelectedRow();
                if (sel >= 0) {
                    ingModel.removeRow(sel);
                }
            });

            JPanel ingBottom = new JPanel(new BorderLayout(0, 6));
            ingBottom.setBackground(BG_DARK);
            ingBottom.add(addIngRow, BorderLayout.NORTH);
            ingBottom.add(delIng, BorderLayout.SOUTH);
            right.add(ingBottom, BorderLayout.SOUTH);
            gc.gridx = 0;
            gc.gridy = 0;
            gc.weightx = 0.48;
            gc.weighty = 1;
            gc.fill = GridBagConstraints.BOTH;
            form.add(left, gc);
            gc.gridx = 1;
            gc.weightx = 0.52;
            form.add(right, gc);
            JPanel footer = new JPanel(new FlowLayout(FlowLayout.RIGHT, 16, 12));
            footer.setBackground(BG_DARK);
            footer.setBorder(new MatteBorder(1, 0, 0, 0, BORDER_C));
            JButton reset = ghostBtn("Limpiar", TEXT_SEC);
            JButton guardar = accentBtn("💾 Guardar receta");
            reset.addActionListener(e -> limpiar());
            guardar.addActionListener(e -> guardar());
            footer.add(reset);
            footer.add(guardar);
            add(form, BorderLayout.CENTER);
            add(footer, BorderLayout.SOUTH);
        }

        void limpiar() {
            fNombre.setText("");
            fTipo.setText("");
            fTiempo.setText("");
            fPasos.setText("");
            while (ingModel.getRowCount() > 0) {
                ingModel.removeRow(0);
            }
        }

        void guardar() {
            String nombre = fNombre.getText().trim();
            String tipo = fTipo.getText().trim();
            String tiempo = fTiempo.getText().trim();
            if (nombre.isEmpty() || tipo.isEmpty() || tiempo.isEmpty()) {
                showError("Nombre, tipo y tiempo son obligatorios.");
                return;
            }
            int min;
            try {
                min = Integer.parseInt(tiempo);
                if (min < 0) {
                    throw new NumberFormatException();
                }
            } catch (NumberFormatException e) {
                showError("El tiempo debe ser un número entero positivo.");
                return;
            }
            if (ingModel.getRowCount() == 0) {
                showError("Agrega al menos un ingrediente.");
                return;
            }

            Receta r = new Receta(0, nombre, tipo, min);
            for (int i = 0; i < ingModel.getRowCount(); i++) {
                String n = ingModel.getValueAt(i, 0).toString();
                double c = ((Number) ingModel.getValueAt(i, 1)).doubleValue();
                String u = ingModel.getValueAt(i, 2).toString();
                r.agregarIngrediente(new Ingrediente(n, c, u));
            }
            String[] lineas = fPasos.getText().split("\n");
            for (String p : lineas) {
                if (!p.trim().isEmpty()) {
                    r.agregarPaso(p.trim());
                }
            }

            servicio.guardarReceta(r);
            showSuccess("¡Receta \"" + nombre + "\" guardada correctamente!");
            limpiar();
            panelListado.refresh();
        }
    }

    class PanelFavoritas extends JPanel {

        JPanel grid = new JPanel(new GridLayout(0, 2, 16, 16));

        PanelFavoritas() {
            setLayout(new BorderLayout());
            setBackground(BG_DARK);
            add(pageHeader("⭐ Recetas favoritas", "Tus recetas marcadas como favoritas"), BorderLayout.NORTH);
            grid.setBackground(BG_DARK);
            grid.setBorder(new EmptyBorder(0, 32, 32, 32));
            add(darkScroll(grid), BorderLayout.CENTER);
        }

        void refresh() {
            grid.removeAll();
            List<Receta> favs = servicio.obtenerFavoritas();
            if (favs.isEmpty()) {
                JLabel empty = label("No tienes recetas favoritas aún. Marca alguna con ⭐", FONT_H3, TEXT_SEC);
                empty.setHorizontalAlignment(SwingConstants.CENTER);
                grid.add(empty);
            } else {
                for (Receta r : favs) {
                    JPanel card = card(14);
                    card.setOpaque(false);
                    card.setLayout(new BorderLayout(0, 8));
                    card.setBorder(new EmptyBorder(18, 20, 18, 20));
                    JLabel nom = label("⭐ " + r.getNombre(), FONT_H2, ACCENT2);
                    JLabel inf = label(r.getTipoCocina() + " · " + r.getTiempoPreparacion() + " min", FONT_BODY, TEXT_SEC);
                    JButton ver = accentBtn("Ver receta");
                    ver.setFont(FONT_SMALL);
                    ver.setBorder(new EmptyBorder(5, 14, 5, 14));
                    ver.addActionListener(e -> showDetalle(r.getId()));
                    JButton quitarFav = ghostBtn("☆ Quitar", ACCENT2);
                    quitarFav.setFont(FONT_SMALL);
                    quitarFav.addActionListener(e -> {
                        servicio.toggleFavorita(r.getId());
                        refresh();
                    });
                    JPanel btns = new JPanel(new FlowLayout(FlowLayout.LEFT, 8, 0));
                    btns.setOpaque(false);
                    btns.add(ver);
                    btns.add(quitarFav);
                    card.add(nom, BorderLayout.NORTH);
                    card.add(inf, BorderLayout.CENTER);
                    card.add(btns, BorderLayout.SOUTH);
                    grid.add(card);
                }
            }
            grid.revalidate();
            grid.repaint();
        }
    }

    class PanelBusqueda extends JPanel {

        private JComboBox<String> criterioCombo;
        private JTextField campoBusqueda;
        private JPanel panelResultados;

        PanelBusqueda() {
            setLayout(new BorderLayout());
            setBackground(BG_DARK);
            JPanel norte = new JPanel();
            norte.setLayout(new BoxLayout(norte, BoxLayout.Y_AXIS));
            norte.setBackground(BG_DARK);

            JLabel titulo = new JLabel("🔍  Buscar recetas");
            titulo.setFont(FONT_TITLE);
            titulo.setForeground(TEXT_PRI);
            titulo.setAlignmentX(LEFT_ALIGNMENT);
            titulo.setBorder(new EmptyBorder(28, 32, 4, 32));
            norte.add(titulo);

            JLabel sub = new JLabel("Selecciona un criterio, escribe y pulsa Buscar");
            sub.setFont(FONT_BODY);
            sub.setForeground(TEXT_SEC);
            sub.setAlignmentX(LEFT_ALIGNMENT);
            sub.setBorder(new EmptyBorder(0, 32, 16, 32));
            norte.add(sub);

            // Fila de controles con GridBagLayout
            JPanel fila = new JPanel(new GridBagLayout());
            fila.setBackground(BG_DARK);
            fila.setBorder(new EmptyBorder(0, 32, 16, 32));
            fila.setAlignmentX(LEFT_ALIGNMENT);
            fila.setMaximumSize(new Dimension(Integer.MAX_VALUE, 46));

            GridBagConstraints g = new GridBagConstraints();
            g.fill = GridBagConstraints.BOTH;
            g.insets = new Insets(0, 0, 0, 8);
            g.gridy = 0;

            g.gridx = 0;
            g.weightx = 0;
            JLabel lbl = new JLabel("Buscar por:");
            lbl.setFont(FONT_H3);
            lbl.setForeground(TEXT_SEC);
            fila.add(lbl, g);

            g.gridx = 1;
            g.weightx = 0;
            criterioCombo = new JComboBox<>(new String[]{
                "Ingrediente", "Tipo de cocina", "Tiempo máximo (min)"
            });
            criterioCombo.setFont(FONT_BODY);
            criterioCombo.setPreferredSize(new Dimension(190, 36));
            fila.add(criterioCombo, g);

            g.gridx = 2;
            g.weightx = 1;
            g.insets = new Insets(0, 0, 0, 8);
            campoBusqueda = new JTextField();
            campoBusqueda.setFont(FONT_BODY);
            campoBusqueda.addActionListener(e -> ejecutarBusqueda());
            fila.add(campoBusqueda, g);

            g.gridx = 3;
            g.weightx = 0;
            g.insets = new Insets(0, 0, 0, 0);
            JButton btn = new JButton("Buscar");
            btn.setFont(FONT_H3);
            btn.setBackground(ACCENT);
            btn.setForeground(Color.WHITE);
            btn.setOpaque(true);
            btn.setFocusPainted(false);
            btn.setPreferredSize(new Dimension(95, 36));
            btn.addActionListener(e -> ejecutarBusqueda());
            fila.add(btn, g);

            norte.add(fila);

            JSeparator sep = new JSeparator();
            sep.setForeground(BORDER_C);
            sep.setMaximumSize(new Dimension(Integer.MAX_VALUE, 1));
            sep.setAlignmentX(LEFT_ALIGNMENT);
            JPanel sepWrap = new JPanel(new BorderLayout());
            sepWrap.setBackground(BG_DARK);
            sepWrap.setBorder(new EmptyBorder(4, 32, 0, 32));
            sepWrap.setMaximumSize(new Dimension(Integer.MAX_VALUE, 14));
            sepWrap.setAlignmentX(LEFT_ALIGNMENT);
            sepWrap.add(sep);
            norte.add(sepWrap);

            add(norte, BorderLayout.NORTH);

            // ── Panel CENTER: resultados ─────────────────────────
            panelResultados = new JPanel();
            panelResultados.setLayout(new BoxLayout(panelResultados, BoxLayout.Y_AXIS));
            panelResultados.setBackground(BG_DARK);
            panelResultados.setBorder(new EmptyBorder(16, 32, 32, 32));

            JScrollPane scroll = new JScrollPane(panelResultados);
            scroll.setBorder(null);
            scroll.setBackground(BG_DARK);
            scroll.getViewport().setBackground(BG_DARK);
            add(scroll, BorderLayout.CENTER);
        }

        private void ejecutarBusqueda() {
            panelResultados.removeAll();

            String texto = campoBusqueda.getText().trim();
            int idx = criterioCombo.getSelectedIndex();

            if (texto.isEmpty()) {
                JLabel warn = new JLabel("  ⚠  Escribe algo en el campo de texto antes de buscar.");
                warn.setFont(FONT_H3);
                warn.setForeground(ACCENT);
                warn.setAlignmentX(LEFT_ALIGNMENT);
                panelResultados.add(warn);
                panelResultados.revalidate();
                panelResultados.repaint();
                return;
            }

            List<Receta> lista;
            try {
                lista = switch (idx) {
                    case 0 ->
                        servicio.buscarPorIngrediente(texto);
                    case 1 ->
                        servicio.buscarPorTipoCocina(texto);
                    case 2 ->
                        servicio.buscarPorTiempoMaximo(Integer.parseInt(texto));
                    default ->
                        List.of();
                };
            } catch (NumberFormatException e) {
                showError("Para buscar por tiempo escribe un número entero. Ejemplo: 30");
                return;
            }

            if (lista.isEmpty()) {
                JLabel vacio = new JLabel("  Sin resultados para \"" + texto + "\"");
                vacio.setFont(FONT_H3);
                vacio.setForeground(TEXT_SEC);
                vacio.setAlignmentX(LEFT_ALIGNMENT);
                panelResultados.add(vacio);
            } else {
                JLabel info = new JLabel("  " + lista.size() + " resultado(s):");
                info.setFont(FONT_H3);
                info.setForeground(TEXT_SEC);
                info.setAlignmentX(LEFT_ALIGNMENT);
                info.setBorder(new EmptyBorder(0, 0, 10, 0));
                panelResultados.add(info);

                for (Receta r : lista) {
                    JPanel fila = new JPanel(new BorderLayout(16, 0));
                    fila.setBackground(BG_CARD);
                    fila.setMaximumSize(new Dimension(Integer.MAX_VALUE, 70));
                    fila.setAlignmentX(LEFT_ALIGNMENT);
                    fila.setBorder(new CompoundBorder(
                            new MatteBorder(0, 0, 1, 0, BORDER_C),
                            new EmptyBorder(12, 16, 12, 16)));

                    JPanel textos = new JPanel(new GridLayout(2, 1, 0, 3));
                    textos.setOpaque(false);
                    JLabel nombre = new JLabel((r.isFavorita() ? "⭐ " : "") + r.getNombre());
                    nombre.setFont(FONT_H2);
                    nombre.setForeground(TEXT_PRI);
                    JLabel detalles = new JLabel("🍴 " + r.getTipoCocina()
                            + "    ⏱ " + r.getTiempoPreparacion() + " min"
                            + "    🧂 " + r.getIngredientes().size() + " ingredientes");
                    detalles.setFont(FONT_SMALL);
                    detalles.setForeground(TEXT_SEC);
                    textos.add(nombre);
                    textos.add(detalles);
                    fila.add(textos, BorderLayout.CENTER);

                    JButton ver = new JButton("Ver receta");
                    ver.setFont(FONT_BODY);
                    ver.setBackground(ACCENT);
                    ver.setForeground(Color.WHITE);
                    ver.setFocusPainted(false);
                    ver.setBorder(new EmptyBorder(6, 14, 6, 14));
                    ver.addActionListener(e -> showDetalle(r.getId()));
                    fila.add(ver, BorderLayout.EAST);

                    panelResultados.add(fila);
                    panelResultados.add(Box.createVerticalStrut(6));
                }
            }

            panelResultados.revalidate();
            panelResultados.repaint();
        }
    }

    class PanelCompras extends JPanel {

        JPanel checkList = new JPanel();
        JPanel listaResult = new JPanel(new BorderLayout(0, 12));
        List<JCheckBox> checks = new ArrayList<>();

        PanelCompras() {
            setLayout(new BorderLayout());
            setBackground(BG_DARK);
            add(pageHeader("🛒 Lista de compras", "Selecciona recetas y genera tu lista de ingredientes"), BorderLayout.NORTH);
            JPanel body = new JPanel(new GridLayout(1, 2, 20, 0));
            body.setBackground(BG_DARK);
            body.setBorder(new EmptyBorder(0, 32, 32, 32));
            JPanel selCard = card(14);
            selCard.setOpaque(false);
            selCard.setLayout(new BorderLayout(0, 12));
            selCard.setBorder(new EmptyBorder(20, 20, 20, 20));
            selCard.add(label("Selecciona recetas:", FONT_H2, TEXT_PRI), BorderLayout.NORTH);
            checkList.setLayout(new BoxLayout(checkList, BoxLayout.Y_AXIS));
            checkList.setBackground(BG_CARD);
            selCard.add(darkScroll(checkList), BorderLayout.CENTER);
            JButton genBtn = accentBtn("🛒 Generar lista");
            genBtn.addActionListener(e -> generar());
            selCard.add(genBtn, BorderLayout.SOUTH);
            JPanel resCard = card(14);
            resCard.setOpaque(false);
            resCard.setLayout(new BorderLayout(0, 12));
            resCard.setBorder(new EmptyBorder(20, 20, 20, 20));
            resCard.add(label("Tu lista de compras:", FONT_H2, TEXT_PRI), BorderLayout.NORTH);
            listaResult.setOpaque(false);
            resCard.add(listaResult, BorderLayout.CENTER);

            body.add(selCard);
            body.add(resCard);
            add(body, BorderLayout.CENTER);
            addComponentListener(new ComponentAdapter() {
                @Override
                public void componentShown(ComponentEvent e) {
                    refreshChecks();
                }
            });
        }

        void refreshChecks() {
            checkList.removeAll();
            checks.clear();
            for (Receta r : servicio.obtenerTodasLasRecetas()) {
                JCheckBox cb = new JCheckBox(r.getNombre());
                cb.setFont(FONT_BODY);
                cb.setForeground(TEXT_PRI);
                cb.setBackground(BG_CARD);
                cb.setOpaque(false);
                cb.setBorder(new EmptyBorder(8, 8, 8, 8));
                cb.putClientProperty("id", r.getId());
                checks.add(cb);
                checkList.add(cb);
            }
            checkList.revalidate();
            checkList.repaint();
        }

        void generar() {
            List<Integer> ids = new ArrayList<>();
            for (JCheckBox cb : checks) {
                if (cb.isSelected()) {
                    ids.add((Integer) cb.getClientProperty("id"));
                }
            }
            if (ids.isEmpty()) {
                showError("Selecciona al menos una receta.");
                return;
            }

            Map<String, String> lista = servicio.generarListaDeCompras(ids);
            listaResult.removeAll();

            JPanel items = new JPanel();
            items.setLayout(new BoxLayout(items, BoxLayout.Y_AXIS));
            items.setOpaque(false);
            for (Map.Entry<String, String> e : lista.entrySet()) {
                JPanel row = new JPanel(new BorderLayout(8, 0));
                row.setOpaque(false);
                row.setBorder(new EmptyBorder(7, 0, 7, 0));
                String nombre = e.getKey().substring(0, 1).toUpperCase() + e.getKey().substring(1);
                JCheckBox chk = new JCheckBox(nombre);
                chk.setFont(FONT_BODY);
                chk.setForeground(TEXT_PRI);
                chk.setBackground(new Color(0, 0, 0, 0));
                chk.setOpaque(false);
                JLabel qty = label(e.getValue(), FONT_BODY, ACCENT);
                qty.setHorizontalAlignment(SwingConstants.RIGHT);
                row.add(chk, BorderLayout.CENTER);
                row.add(qty, BorderLayout.EAST);
                JSeparator sep = new JSeparator();
                sep.setForeground(BORDER_C);
                JPanel wrap = new JPanel(new BorderLayout());
                wrap.setOpaque(false);
                wrap.add(row, BorderLayout.CENTER);
                wrap.add(sep, BorderLayout.SOUTH);
                items.add(wrap);
            }
            listaResult.add(darkScroll(items), BorderLayout.CENTER);
            JLabel total = label("Total: " + lista.size() + " ingredientes", FONT_H3, SUCCESS);
            listaResult.add(total, BorderLayout.NORTH);
            listaResult.revalidate();
            listaResult.repaint();
        }
    }

    private void showError(String msg) {
        JOptionPane.showMessageDialog(this, msg, "Error", JOptionPane.ERROR_MESSAGE);
    }

    private void showSuccess(String msg) {
        JOptionPane.showMessageDialog(this, msg, "Éxito", JOptionPane.INFORMATION_MESSAGE);
    }

    /**
     * Inicializa UIManager y lanza la ventana. Llamado desde App.main()
     */
    public static void main(String[] args) {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception ignored) {
        }
        UIManager.put("OptionPane.background", BG_PANEL);
        UIManager.put("Panel.background", BG_PANEL);
        UIManager.put("OptionPane.messageForeground", TEXT_PRI);
        UIManager.put("Button.background", BG_CARD);
        UIManager.put("Button.foreground", TEXT_PRI);
        UIManager.put("ComboBox.background", BG_CARD);
        UIManager.put("ComboBox.foreground", TEXT_PRI);
        UIManager.put("ComboBox.selectionBackground", ACCENT);

        RecetaRepository repo = new RecetaRepository();
        RecetaService svc = new RecetaService(repo);
        DatosEjemplo.cargar(svc);

        SwingUtilities.invokeLater(() -> {
            MainSwing_p app = new MainSwing_p(svc);
            app.setVisible(true);
        });
    }
}
