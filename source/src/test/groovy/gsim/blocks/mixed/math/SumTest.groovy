package gsim.blocks.mixed.math

import gsim.SimulationParameters
import gsim.core.blocks.BlockType
import org.junit.Test
import static org.junit.Assert.assertEquals
import gsim.core.blocks.variables.Variable
import gsim.core.blocks.variables.VariableType

/**
 *
 * @author Richard Mihalovič
 */
class SumTest {

	@Test
	void defaultValues() {
		def b = new Sum()

		assertEquals('Sumator', b.getBlockName())
        assertEquals(BlockType.MIXED, b.getBlockType())
		assertEquals('++', b.getParameter('operations'))
	}

	@Test
	void calculateOutputValueSumSum() {
		def b = new Sum()

		// operations: ++
		// 10 + 20 = 30
		def inputParameters = []
		inputParameters[1] = Variable.number(10.0)
		inputParameters[2] = Variable.number(20.0)

		b.simulationStep(inputParameters, new SimulationParameters())

		assertEquals(b.getOutput(1).value, 30.0, 0.0)
		assertEquals(b.getOutput(1).type, VariableType.NUMBER)
	}

	@Test
	void calculateOutputValueSumSumSum() {
		def b = new Sum()
		b.setParameters([operations: '+++'])

		// operations: +++
		// 10 + 10 + 10 = 30
		def inputParameters = []
		inputParameters[1] = Variable.number(10.0)
		inputParameters[2] = Variable.number(10.0)
		inputParameters[3] = Variable.number(10.0)
		b.simulationStep(inputParameters, new SimulationParameters())

		assertEquals(b.getOutput(1).value, 30.0, 0.0)
		assertEquals(b.getOutput(1).type, VariableType.NUMBER)
	}

	@Test
	void calculateOutputValuePlusMinus() {
		def b = new Sum()
		b.setParameters([operations: '+-'])

		// operations: +-
		// 20 - 10 = 10
		def inputParameters = []
		inputParameters[1] = Variable.number(20.0)
		inputParameters[2] = Variable.number(10.0)
		b.simulationStep(inputParameters, new SimulationParameters())

		assertEquals(b.getOutput(1).value, 10.0, 0.0)
		assertEquals(b.getOutput(1).type, VariableType.NUMBER)
	}

//	@Test(expected = IllegalArgumentException.class)
//	void invalidOptions() {
//		def b = new Sum()
//		b.setParameters([operations: '+x'])
//	}
}