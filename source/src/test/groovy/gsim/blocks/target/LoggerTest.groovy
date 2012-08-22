import org.junit.Test
import static org.junit.Assert.*

import gsim.core.blocks.variables.VariableType
import gsim.core.blocks.variables.InputVariable
import gsim.core.blocks.BlockType

/**
 *
 * @author Richard Mihalovič
 */
class LoggerTest {

	@Test
	void blockDefaultValues() {
		def b = new gsim.blocks.target.Logger()

		assertEquals(0, b.getValues().size())
        assertEquals(0, b.getAvailableOutputs().size())
		assertEquals('Logger', b.getBlockName())
        assertEquals(BlockType.TARGET, b.getBlockType())
	}

	@Test
	void addBlockValues() {
		def b = new gsim.blocks.target.Logger()
		b.addValue(1)
		b.addValue(2)
		b.addValue(3)

		def values = b.getValues()
		def delta = 0
		
		assertTrue(values.size() == 3)

		assertEquals(1, values[0], delta)
		assertEquals(2, values[1], delta)
		assertEquals(3, values[2], delta)
	}

	@Test
	void getInputVariables() {
		def b = new gsim.blocks.target.Logger()

		def inputs = b.getAvailableInputs()
		def input = inputs[0]

		assertEquals(1, inputs.size())
		assertEquals(InputVariable.class, input.class)
		assertEquals(VariableType.NUMBER, input.getType())
		assertEquals(true, input.isRequired())
	}
}