import javax.swing.*
import java.awt.*
import java.awt.event.ActionEvent

class NumberPair(
    var field1: Double,
    var field2: Double
) {
    constructor() : this(0.0, 0.0)

    fun info(): String =
        "Поле 1 = $field1, Поле 2 = $field2"

    fun halfDifference(): Double =
        (field1 - field2) / 2.0
}

fun main() {
    SwingUtilities.invokeLater {
        MainWindow()
    }
}

class MainWindow : JFrame("Полу-разность чисел") {

    private val editField1 = JTextField(15)
    private val editField2 = JTextField(15)

    private val memo = JTextArea(10, 30).apply {
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

        addRow(0, "Поле 1:", editField1)
        addRow(1, "Поле 2:", editField2)

        val calcButton = JButton("Вычислить")
        gbc.gridy = 2
        gbc.gridx = 0
        gbc.gridwidth = 2
        gbc.fill = GridBagConstraints.HORIZONTAL
        inputPanel.add(calcButton, gbc)

        add(inputPanel, BorderLayout.NORTH)
        add(JScrollPane(memo), BorderLayout.CENTER)

        calcButton.addActionListener { e: ActionEvent? ->
            onCalculate()
        }

        pack()
        setLocationRelativeTo(null)
        isVisible = true
    }

    private fun onCalculate() {
        try {
            val v1 = editField1.text.trim().replace(',', '.').toDouble()
            val v2 = editField2.text.trim().replace(',', '.').toDouble()

            val obj = NumberPair(v1, v2)

            memo.append("Информация: ${obj.info()}\n")
            memo.append("Полу-разность = ${obj.halfDifference()}\n")
            memo.append("-----------------------------------\n")
        } catch (e: NumberFormatException) {
            JOptionPane.showMessageDialog(
                this,
                "Введите корректные вещественные числа в оба поля!",
                "Ошибка ввода",
                JOptionPane.ERROR_MESSAGE
            )
        }
    }
}