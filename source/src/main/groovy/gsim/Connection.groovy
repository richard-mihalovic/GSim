package gsim

import gsim.core.blocks.Block
import gsim.core.blocks.BlockType

/**
 *
 * @author Richard Mihalovič
 */
class Connection {
	Block sourceBlock = null
	String sourceValuePath = ''

	Block targetBlock = null
	String targetValuePath = ''

	def getSourceValue() {
		def value = parsePath(sourceValuePath)
		if(value != null) {
			def valueType = (String) value[0]
			def valuePosition = (Integer) value[1]
			
			if(valueType.toUpperCase() == 'I')
				sourceBlock.getInput(valuePosition)
			else if (valueType.toUpperCase() == 'O')
				sourceBlock.getOutput(valuePosition)
		} else throw new IllegalArgumentException()
	}

	def getTargetValue() {
		def value = parsePath(targetValuePath)
		if(value != null) {
			def String valueType = value[0]
			def Integer valuePosition = value[1]

			if(valueType.toUpperCase() == 'I') {
				if(targetBlock.getBlockType() == BlockType.SOURCE)
					targetBlock.getOutput(valuePosition)
				else
					targetBlock.getInput(valuePosition)
			} else if (valueType.toUpperCase() == 'O') {
				if(targetBlock.getBlockType() == BlockType.TARGET)
					targetBlock.getInput(valuePosition)
				else
					targetBlock.getOutput(valuePosition)
			}
		} else throw new IllegalArgumentException()
	}

	/**
	 * Example of path: 'I(1)', 'O(7)'
	*/
	def parsePath(path) {
		def parsedValues = path =~ /([IiOo]{1})\(([0-9]{1,2})\)/

		if(parsedValues.size() == 0)
			return null
		else
			return [
				(String) parsedValues[0][1],
				Integer.parseInt(parsedValues[0][2])
			]
	}

	/*
	INPUT: O1, O(5)
	 */
    def Integer getOutputPathIndex(String path) {
		def index = -1
		def parsedValues = path =~ /[Oo]{1}\(?([0-9]{1,2})?\)?/

		if(parsedValues.size() != 0)
			index = Integer.parseInt(parsedValues[0][1])

		return index
    }

	/*
	INPUT: I1, I(5)
	 */
    def Integer pathToIndex(String path) {
		def index = -1
		def parsedValues = path =~ /[IiOo]{1}\(?([0-9]{1,2})?\)?/

		if(parsedValues.size() != 0)
			index = Integer.parseInt(parsedValues[0][1])

		return index
    }
}

