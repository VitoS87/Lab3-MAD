import javax.swing.*
import java.awt.*

open class NumberPair(
    var field1: Double,  
    var field2: Double    
) {
    constructor() : this(0.0, 0.0)

    open fun info(): String =
        "Поле 1 (a) = $field1, Поле 2 (b) = $field2"

    open fun halfDifference(): Double =
        (field1 - field2) / 2.0
}

class NumberPairExt(
    field1: Double,
    field2: Double,
    var c: Double
) : NumberPair(field1, field2) {

    constructor() : this(0.0, 0.0, 0.0)

    override fun info(): String =
        "${super.info()}, Поле 3 (c) = $c"

    fun productWithC(): Double =
        halfDifference() * c
}

fun main() {
    SwingUtilities.invokeLater { MainWindow() }
}

class MainWindow : JFrame("Класс-родитель и класс-потомок") {

    private val editA = JTextField(15)
    private val editB = JTextField(15)

    private val editC = JTextField(15)

    private val memo = JTextArea(12, 34).apply {
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

        addRow(0, "Поле 1 (a):", editA)
        addRow(1, "Поле 2 (b):", editB)

        val sepLabel = JLabel("── Доп. поле класса-потомка ──")
        gbc.gridy = 2; gbc.gridx = 0; gbc.gridwidth = 2
        inputPanel.add(sepLabel, gbc)
        gbc.gridwidth = 1

        addRow(3, "Поле 3 (c):", editC)

        val btnParent = JButton("Родитель: полу-разность")
        val btnChild  = JButton("Потомок: произведение")

        gbc.gridy = 4; gbc.gridx = 0; gbc.gridwidth = 2
        gbc.fill = GridBagConstraints.HORIZONTAL
        inputPanel.add(btnParent, gbc)

        gbc.gridy = 5
        inputPanel.add(btnChild, gbc)
        gbc.fill = GridBagConstraints.NONE

        add(inputPanel, BorderLayout.NORTH)
        add(JScrollPane(memo), BorderLayout.CENTER)

        btnParent.addActionListener { onCalcParent() }
        btnChild.addActionListener  { onCalcChild() }

        pack()
        setLocationRelativeTo(null)
        isVisible = true
    }

    private fun parseField(field: JTextField): Double =
        field.text.trim().replace(',', '.').toDouble()

    private fun onCalcParent() {
        try {
            val a = parseField(editA)
            val b = parseField(editB)
            val obj = NumberPair(a, b)

            memo.append(">>> Класс-родитель NumberPair\n")
            memo.append("Информация: ${obj.info()}\n")
            memo.append("Полу-разность = ${obj.halfDifference()}\n")
            memo.append("-----------------------------------\n")
        } catch (e: NumberFormatException) {
            showError("Введите корректные числа a и b!")
        }
    }
    
    private fun onCalcChild() {
        try {
            val a = parseField(editA)
            val b = parseField(editB)
            val c = parseField(editC)
            val obj = NumberPairExt(a, b, c)

            memo.append(">>> Класс-потомок NumberPairExt\n")
            memo.append("Информация: ${obj.info()}\n")
            memo.append("Полу-разность (a,b) = ${obj.halfDifference()}\n")
            memo.append("Произведение полу-разности на c = ${obj.productWithC()}\n")
            memo.append("-----------------------------------\n")
        } catch (e: NumberFormatException) {
            showError("Введите корректные числа a, b и c!")
        }
    }

    private fun showError(msg: String) {
        JOptionPane.showMessageDialog(this, msg, "Ошибка ввода", JOptionPane.ERROR_MESSAGE)
    }
}
