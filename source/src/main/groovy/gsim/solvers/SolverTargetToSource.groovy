package gsim.solvers

import gsim.core.blocks.BlockType
import gsim.core.blocks.Block
import gsim.Connection

/**
 * @author Richard Mihalovič
 */
class SolverTargetToSource extends SchemaSolver {
    def computeSimulationStep() {
        if(schema == null) throw java.util.Exception('Simulatin schema not defined!')

//        print '=== STARTING SOLVER ===' + "\n"
        def targetBlocks = findTargetBlocks()

		targetBlocks.each { targetBlock ->
        	solveConnectionsChain(targetBlock, [])
		}
    }

    private def findTargetBlocks() {
        def java.util.List<Block> targets = []
        schema.getConnections().each { connection ->
            if(connection.getTargetBlock().getBlockType() == BlockType.TARGET)
                targets.add(connection.getTargetBlock())
        }

        return targets
    }

    private def solveConnectionsChain(Block block, connections) {
        def java.util.List<Connection> conns = schema.findConnectionsToBlock(block)
        def inputs = []

		println '.'

        conns.each { connection ->
            def sourceBlock = connection.getSourceBlock()
            def targetBlock = connection.getTargetBlock()

			def inputIndex = connection.pathToIndex(connection.getTargetValuePath())
			def input = null

			if(connections.count(connection) > 0) {
				input = sourceBlock.getOutput(
					connection.getOutputPathIndex(
						connection.getSourceValuePath()
					)
				)
			} else {
				connections.add(connection)
				solveConnectionsChain(sourceBlock, connections)

				input = sourceBlock.getOutput(
					connection.getOutputPathIndex(
						connection.getSourceValuePath()
					)
				)
			}
			inputs[inputIndex] = input
        }
        block.simulationStep(inputs, simulationParameters)

//		println 'INPUTS: ' + inputs
//		if(block.getBlockType() != BlockType.TARGET)
//			print 'OUT: ' + block.getBlockId() + ' >> ' + block.getOutput(1) + "\n"
//		else
//			print 'OUT: ' + block.getBlockId() + ' >> ' + block.getInput(1) + "\n"

		//return (block.getBlockType() != BlockType.TARGET) ? block.getOutput(1) : block.getInput(1)
    }
}
