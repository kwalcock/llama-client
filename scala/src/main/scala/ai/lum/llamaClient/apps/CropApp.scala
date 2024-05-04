package ai.lum.llamaClient.apps

import de.kherud.llama.{InferenceParameters, LlamaModel, ModelParameters}
import de.kherud.llama.args.MiroStat

import java.nio.charset.StandardCharsets
import scala.io.Source
import scala.util.Using

object CropApp extends App {
  val modelFilename = "/Users/kwa/.cache/huggingface/hub/models--TheBloke--openchat-3.5-0106-GGUF/blobs/36a74fec42da7c3fc2614ce3444a99868647342ff4789f0e0d82882d7735aab5"
  val grammarFilename = "./src/main/resources/json.gbnf"

  val modelParams = new ModelParameters()
      .setModelFilePath(modelFilename)
      .setNGpuLayers(43)
      .setDisableLog(true)
  val model = new LlamaModel(modelParams)
  val grammar = Using.resource(Source.fromFile(grammarFilename)(StandardCharsets.UTF_8)) { source =>
    source.getLines.mkString("\n")
  }

  val prompts = Seq(
    """
      |<speaker_1> Okay. Very cool. So the 2021 was probably grain sorghum then?
      |<speaker_2> Yeah. 21 would've been grain sorghum. Yeah.
      |
      |
      |You are an advanced artificial intelligence system designed to extract key agricultural information from conversations.
      |Given the critical importance of accuracy in agricultural contexts—where decisions based on this information can significantly impact crop yield, financial outcomes, and resource management—you must ensure exceptionally high accuracy in your extractions.
      |
      |Instructions:
      |1. **Read Carefully**: Read the preceding text carefully to identify and extract information relevant to agricultural contexts.
      |2. **Schema Compliance**: Use the following schema to guide your information extraction process and format your responses:
      |   {"description": "An entry of agricultural information mentioned in a conversation.", "properties": {"crop": {"anyOf": [{"type": "string"}, {"type": "null"}], "default": null, "description": "The type of crop that was planted."}, "date": {"anyOf": [{"type": "string"}, {"type": "null"}], "default": null, "description": "The date when the crop was planted. This can include a specific date, a month, or just a year. The format may vary, and the field should capture the most specific date information available. If only a part of the date is mentioned (e.g., just the month or season), this should still be captured."}}, "type": "object"}
      |
      |3. **JSON Format**: Return the extracted data in JSON format. Follow the example provided to ensure accuracy in your response format:
      |   From a statement like "We planted corn in early May in Iowa using no-till," the extracted information should be:
      |[{"crop": "corn", "date": "early May"}]
      |
      |4. **Exclusivity and Precision**: Do not include any information that is not explicitly mentioned in the text. Analyze the text carefully to ensure all requested data is extracted accurately and included only once in your response.
      |5. **Adherence to Format**: Adhere strictly to the response format. Ensure that your JSON output does not contain extra spaces or text that deviates from the specified schema.
      |
      |Your role is crucial in supporting informed decision-making in the agricultural sector by providing reliable, timely, and context-aware data extraction. Adapt and refine your extraction techniques based on feedback and evolving agricultural practices to maintain relevance and accuracy over time.
      |""".stripMargin,

    """
      |<speaker_1> Yes.
      |<speaker_2> All right. And do you have a yield for that?
      |<speaker_1> That's usually all over the board, depending on how much moisture we get, but let's see. Try to hit 5 round bales to the acre for first cutting and that would be 800 pound bales. 2, trying to make my brain learn.
      |<speaker_2> and we're giving you some math problems here.
      |
      |
      |You are an advanced artificial intelligence system designed to extract key agricultural information from conversations.
      |Given the critical importance of accuracy in agricultural contexts—where decisions based on this information can significantly impact crop yield, financial outcomes, and resource management—you must ensure exceptionally high accuracy in your extractions.
      |
      |Instructions:
      |1. **Read Carefully**: Read the preceding text carefully to identify and extract information relevant to agricultural contexts.
      |2. **Schema Compliance**: Use the following schema to guide your information extraction process and format your responses:
      |   {"description": "An entry of agricultural information mentioned in a conversation.", "properties": {"crop": {"anyOf": [{"type": "string"}, {"type": "null"}], "default": null, "description": "The type of crop that was planted."}, "date": {"anyOf": [{"type": "string"}, {"type": "null"}], "default": null, "description": "The date when the crop was planted. This can include a specific date, a month, or just a year. The format may vary, and the field should capture the most specific date information available. If only a part of the date is mentioned (e.g., just the month or season), this should still be captured."}}, "type": "object"}
      |
      |3. **JSON Format**: Return the extracted data in JSON format. Follow the example provided to ensure accuracy in your response format:
      |   From a statement like "We planted corn in early May in Iowa using no-till," the extracted information should be:
      |[{"crop": "corn", "date": "early May"}]
      |
      |4. **Exclusivity and Precision**: Do not include any information that is not explicitly mentioned in the text. Analyze the text carefully to ensure all requested data is extracted accurately and included only once in your response.
      |5. **Adherence to Format**: Adhere strictly to the response format. Ensure that your JSON output does not contain extra spaces or text that deviates from the specified schema.
      |
      |Your role is crucial in supporting informed decision-making in the agricultural sector by providing reliable, timely, and context-aware data extraction. Adapt and refine your extraction techniques based on feedback and evolving agricultural practices to maintain relevance and accuracy over time.
      |""".stripMargin,

    """
      |<speaker_1> Yes.
      |<speaker_2> All right. And do you have a yield for that?
      |<speaker_1> That's usually all over the board, depending on how much moisture we get, but let's see. Try to hit 5 round bales to the acre for first cutting and that would be 800 pound bales. 2, trying to make my brain learn.
      |<speaker_2> and we're giving you some math problems here.
      |
      |
      |You are an advanced artificial intelligence system designed to extract key agricultural information from conversations.
      |Given the critical importance of accuracy in agricultural contexts—where decisions based on this information can significantly impact crop yield, financial outcomes, and resource management—you must ensure exceptionally high accuracy in your extractions.
      |
      |Instructions:
      |1. **Read Carefully**: Read the preceding text carefully to identify and extract information relevant to agricultural contexts.
      |2. **Schema Compliance**: Use the following schema to guide your information extraction process and format your responses:
      |   {"description": "A measurement of yield.", "properties": {"amount": {"anyOf": [{"type": "string"}, {"type": "null"}], "default": null, "description": "The numerical value representing the yield."}, "units": {"anyOf": [{"type": "string"}, {"type": "null"}], "default": null, "description": "The units used to measure the yield amount, incorporating relevant qualifiers that describe the yield's characteristics or specifications."}, "description": {"anyOf": [{"type": "string"}, {"type": "null"}], "default": null, "description": "Additional descriptors or specifications associated with the yield, such as weight or quality indicators."}}, "type": "object"}
      |
      |3. **JSON Format**: Return the extracted data in JSON format. Follow the example provided to ensure accuracy in your response format:
      |   For a statement like "We produced 5 barrels total. We are talking about 53 gallon barrels." the extracted information should be:
      |[{"amount": "5", "units": "barrels", "description": "53 gallon barrels"}]
      |
      |4. **Exclusivity and Precision**: Do not include any information that is not explicitly mentioned in the text. Analyze the text carefully to ensure all requested data is extracted accurately and included only once in your response.
      |5. **Adherence to Format**: Adhere strictly to the response format. Ensure that your JSON output does not contain extra spaces or text that deviates from the specified schema.
      |
      |Your role is crucial in supporting informed decision-making in the agricultural sector by providing reliable, timely, and context-aware data extraction. Adapt and refine your extraction techniques based on feedback and evolving agricultural practices to maintain relevance and accuracy over time.
      |""".stripMargin
  )

  val responses = Seq(
    """ [{"crop": "grain sorghum", "date": "2021"}]""",
    """ [{"crop": null, "date": null}]""",
    """[{"amount": "5", "units": "round bales per acre", "description": "First cutting, 800 pound bales"}]"""
  )

  Using.resource(model) { model =>
    prompts.foreach { prompt =>
      val inferenceParameters = new InferenceParameters(prompt)
        // verbose=True
        // .setStream(true) // for some reason this is private
        // json_mode=True
        // Need a grammar as well
        .setGrammar(grammar)
        // .setMaxTokens("") // None
        // .setRole("user")
        .setTemperature(0.0f)
      val output = model.complete(inferenceParameters)
      println("******************************")
      println(output)
      println("******************************")
    }
  }
}
