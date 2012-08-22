package gsim

import gsim.blocks.source.Constant
import gsim.blocks.source.Sin
import gsim.blocks.source.Step
import gsim.core.blocks.Block
import gsim.core.blocks.MixedBlock
import gsim.core.blocks.SourceBlock
import gsim.core.blocks.TargetBlock
import gsim.blocks.mixed.math.*

/**
 *
 * @author Richard Mihalovič
 */
class Schema {
	java.util.List<Block> blocks = []
	java.util.List<Connection> connections = []

	def block(String blockId, String blockType, parameters) {
		def gsim.core.blocks.Block newBlock = null

		switch(blockType) {
			case 'mixed.abs':
				newBlock = new Abs()
				break
			case 'source.constant':
				newBlock = new Constant()
				break
			case 'mixed.derivator':
				newBlock = new Derivator()
				break
			case 'mixed.gain':
				newBlock = new Gain()
				break
			case 'mixed.integrator':
				newBlock = new Integrator()
				break
			case 'mixed.sum':
				newBlock = new Sum()
				break
            case 'mixed.multiply':
                newBlock = new gsim.blocks.mixed.math.Multiply()
                break
            case 'mixed.cos':
                newBlock = new gsim.blocks.mixed.math.Cos();
                break
            case 'mixed.sin':
                newBlock = new gsim.blocks.mixed.math.Sin();
                break
			case 'source.sin':
				newBlock = new Sin()
				break
			case 'source.step':
				newBlock = new Step()
				break
            case 'source.time':
                newBlock = new gsim.blocks.source.Time()
                break
			case 'target.logger':
				newBlock = new gsim.blocks.target.Logger()
				break
			case 'target.scope':
				newBlock = new gsim.blocks.target.Scope()
				break
			case 'target.xyscope':
				newBlock = new gsim.blocks.target.XYScope()
				break
			default:
				throw new Exception('Unknown block type ' + blockType + '!')
		}

		if(newBlock != null) {
			newBlock.setBlockId(blockId)
			newBlock.setParameters(parameters)
		}	

		addBlock(newBlock)

		return newBlock
	}

	def connect(String fromBlock, String toBlock) {
		addConnection(fromBlock, toBlock)
	}

	def connect(String sourceBlock, String sourcePath, String targetBlock, String targetPath) {
		def sourcePathFull = sourceBlock + ':' + sourcePath
		def targetPathFull = targetBlock + ':' + targetPath

		addConnection(sourcePathFull, targetPathFull)
	}

	def findBlock(String blockId) {
		for(block in blocks) {
			if(block.getBlockId()==blockId)
				return block
		}
	
		return null
	}

	/**
	 * Add conection to schema between two blocks, block must be registered in
	 * schema before adding connection.
	 * 
	 * Parameter format is:
	 * I - INPUT
	 * O - OUTPUT
	 * n - position of input / output, starting at 0
	 *
	 * 'BlockName(In)'
	 * 'BlockName(On)'
	 *
	 * Example: 'BLOCK1(I7)', 'BLOCK5()'
	*/
	def addConnection(String sourcePath, String targetPath) {
		def sourceParsed = sourcePath =~ /([A-Za-z0-9]+):([IiOo]{1}[0-9]{1,2})/
		def targetParsed = targetPath =~ /([A-Za-z0-9]+):([IiOo]{1}[0-9]{1,2})/

		if(sourceParsed.size() == 0 || targetParsed.size() == 0)
			throw new IllegalArgumentException()

		def Block source = findBlock(sourceParsed[0][1])
		def Block target = findBlock(targetParsed[0][1])

		def connection = new gsim.Connection(
			sourceBlock: source,
			sourceValuePath: (String)sourceParsed[0][2],
			targetBlock: target,
			targetValuePath: (String)targetParsed[0][2]
		)

		connections.push(connection)
	}

	def addBlock(Block block) {
		blocks.push(block)
	}
	
    private def findConnectionsToBlock(Block block) {
        def blockConnections = []

        connections.each() { connection ->
            if(connection.getTargetBlock() == block)
                blockConnections.add(connection)
        }

        return blockConnections
    }

    private def findConnectionsFromBlock(Block block) {
        def blockConnections = []

        connections.each() { connection ->
            if(connection.getSourceBlock() == block)
                blockConnections.add(connection)
        }

        return blockConnections
    }

	def reset() {
		blocks = []
		connections = []
	}

	def exportToGraphViz() {
		String output = ""

		String targets = ""
		String sources = ""
		String mixed = ""

		blocks.each { block ->
			if(block instanceof SourceBlock)
				sources += block.getBlockId() + ' '
			if(block instanceof TargetBlock)
				targets += block.getBlockId() + ' '
			if(block instanceof MixedBlock)
				mixed += block.getBlockId() + ' '
		}

		output +=
"""
digraph simulation_schema {
rankdir=LR;
node [shape = doublecircle]; $targets;
node [shape = circle]; $sources
node [shape = rectangle]; $mixed

"""
		connections.each { connection ->
			output += connection.getSourceBlock().getBlockId()
			output += ' -> '
			output += connection.getTargetBlock().getBlockId()
			output += '[label = "' + connection.getSourceBlock().getBlockName() + '"]'
			output += ";\n"
		}

		output += '}'
		return output
	}

	def validate() {
		validateBlockParameters()
	}

	private def validateBlockParameters() {
		def List<SchemaValidationError> validationErrors = []

		blocks.each { Block block ->
			block.validateParameters().each { error ->
				validationErrors.add(error)
			}
		}

		return validationErrors
	}
}