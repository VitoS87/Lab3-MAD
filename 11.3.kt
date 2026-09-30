import javax.swing.*
import java.awt.*
import java.awt.event.ActionEvent

open class ComputerNetwork(
    var orgName: String,
    var stations: Int,
    var avgDistance: Double
) {
    constructor() : this("", 0, 0.0)

    open fun quality(): Double =
        stations * avgDistance

    open fun info(): String =
        "Организация: $orgName\n" +
                "Число станций: $stations\n" +
                "Среднее расстояние: $avgDistance м\n" +
                "Q = $stations · $avgDistance = ${quality()}"
}

class ComputerNetworkExt(
    orgName: String,
    stations: Int,
    avgDistance: Double,
    var p: Double
) : ComputerNetwork(orgName, stations, avgDistance) {

    constructor() : this("", 0, 0.0, 0.0)

    override fun quality(): Double =
        super.quality() * p

    override fun info(): String =
        super.info() + "\n" +
                "Скорость P = $p Мб/с\n" +
                "Qp = Q · P = ${super.quality()} · $p = ${quality()}"
}

fun main() {
    SwingUtilities.invokeLater { MainWindow() }
}

class MainWindow : JFrame("Компьютерная сеть — оценка качества") {

    private val editOrg = JTextField(20)
    private val editStations = JTextField(15)
    private val editDistance = JTextField(15)
    private val editP = JTextField(15)

    private val memo = JTextArea(16, 38).apply {
        isEditable = false
        font = Font("Monospaced", Font.PLAIN, 13)
    }

    init {
        defaultCloseOperation = EXIT_ON_CLOSE
        layout = BorderLayout(10, 10)

        val inputPanel = JPanel(GridBagLayout())
        val gbc = GridBagConstraints().apply {
            insets = Insets(5, 5, 5, 5)
            anchor = GridBagConstraints.WEST
        }

        fun addRow(row: Int, label: String, field: JComponent) {
            gbc.gridy = row
            gbc.gridx = 0; inputPanel.add(JLabel(label), gbc)
            gbc.gridx = 1; inputPanel.add(field, gbc)
        }

        addRow(0, "Название организации:", editOrg)
        addRow(1, "Число станций:", editStations)
        addRow(2, "Среднее расстояние (м):", editDistance)

        val sepLabel = JLabel("── Поле класса-потомка ──")
        gbc.gridy = 3; gbc.gridx = 0; gbc.gridwidth = 2
        inputPanel.add(sepLabel, gbc)
        gbc.gridwidth = 1

        addRow(4, "Скорость P (Мб/с):", editP)

        val btnLevel1 = JButton("Класс 1-го уровня: вычислить Q")
        val btnLevel2 = JButton("Класс 2-го уровня: вычислить Qp")

        gbc.gridy = 5; gbc.gridx = 0; gbc.gridwidth = 2
        gbc.fill = GridBagConstraints.HORIZONTAL
        inputPanel.add(btnLevel1, gbc)

        gbc.gridy = 6
        inputPanel.add(btnLevel2, gbc)
        gbc.fill = GridBagConstraints.NONE

        add(inputPanel, BorderLayout.NORTH)
        add(JScrollPane(memo), BorderLayout.CENTER)

        btnLevel1.addActionListener { onCalcLevel1() }
        btnLevel2.addActionListener { onCalcLevel2() }

        pack()
        setLocationRelativeTo(null)
        isVisible = true
    }

    private fun parseField(field: JTextField): String =
        field.text.trim()

    private fun parseDouble(field: JTextField): Double =
        field.text.trim().replace(',', '.').toDouble()

    private fun parseInt(field: JTextField): Int =
        field.text.trim().toInt()

    private fun onCalcLevel1() {
        try {
            val org = parseField(editOrg)
            if (org.isEmpty()) {
                showError("Введите название организации!")
                return
            }
            val st = parseInt(editStations)
            val dist = parseDouble(editDistance)
            val obj = ComputerNetwork(org, st, dist)

            memo.append(">>> Класс 1-го уровня (ComputerNetwork)\n")
            memo.append("${obj.info()}\n")
            memo.append("-----------------------------------\n")
        } catch (e: NumberFormatException) {
            showError("Введите корректные числовые значения!")
        }
    }

    private fun onCalcLevel2() {
        try {
            val org = parseField(editOrg)
            if (org.isEmpty()) {
                showError("Введите название организации!")
                return
            }
            val st = parseInt(editStations)
            val dist = parseDouble(editDistance)
            val p = parseDouble(editP)
            val obj = ComputerNetworkExt(org, st, dist, p)

            memo.append(">>> Класс 2-го уровня (ComputerNetworkExt)\n")
            memo.append("${obj.info()}\n")
            memo.append("-----------------------------------\n")
        } catch (e: NumberFormatException) {
            showError("Введите корректные числовые значения!")
        }
    }

    private fun showError(msg: String) {
        JOptionPane.showMessageDialog(this, msg, "Ошибка ввода", JOptionPane.ERROR_MESSAGE)
    }
}