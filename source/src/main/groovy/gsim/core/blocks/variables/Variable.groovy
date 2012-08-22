package gsim.core.blocks.variables

/**
 * @author Richard Mihalovič
 */
class Variable {
	def VariableType type = VariableType.NUMBER
	def value

	static def number(numberValue) {
		new Variable(
			type: VariableType.NUMBER,
			value: numberValue
		)
	}
}
