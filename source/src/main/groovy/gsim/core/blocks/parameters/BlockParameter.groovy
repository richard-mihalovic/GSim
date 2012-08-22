package gsim.core.blocks

/**
 * @author Richard Mihalovič
 */
class BlockParameterType {
	static def Integer() {
		def regex = /[0-9]+/
		return regex
	}

	static def Decimal() {
		def regex = /[0-9\.]+/
		return regex
	}

	static def Alpha() {
		def regex = /[a-zA-Z]+/
		return regex
	}

	static def AlphaNumeric() {
		def regex = /[a-zA-Z0-9]+/
		return regex
	}

	static def Regex(regex) {
		return regex
	}
}

class BlockParameter {
	def isRequired = false
	def name = null
	def type = null
	def validator = null
}
