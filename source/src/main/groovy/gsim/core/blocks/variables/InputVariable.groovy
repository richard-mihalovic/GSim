package gsim.core.blocks.variables

/**
 *
 * @author Richard Mihalovič
 */
class InputVariable {
	String name
	VariableType type
	Boolean isRequired

	def isRequired() {
		return this.isRequired
	}
}

