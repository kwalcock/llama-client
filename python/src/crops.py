import time

# Cell 1

from huggingface_hub import hf_hub_download

model_path = hf_hub_download(
    repo_id='TheBloke/openchat-3.5-0106-GGUF',
    filename='openchat-3.5-0106.Q5_K_M.gguf',
    local_dir='/Users/kwa/Projects/other/marcovzla/ai_den-project/ai_den/models',
)

model_path

# Cell 2

from ai_den.llama_cpp import LlamaCpp

model = LlamaCpp(model_path)

# Cell 3

import inspect
import docstring_parser
from ai_den.utils.dataclasses import to_json, from_json
from ai_den.utils.json_schema import json_schema

template = """\
{text}

You are an advanced artificial intelligence system designed to extract key agricultural information from conversations.
Given the critical importance of accuracy in agricultural contexts—where decisions based on this information can significantly impact crop yield, financial outcomes, and resource management—you must ensure exceptionally high accuracy in your extractions.

Instructions:
1. **Read Carefully**: Read the preceding text carefully to identify and extract information relevant to agricultural contexts.
2. **Schema Compliance**: Use the following schema to guide your information extraction process and format your responses:
   {schema}

3. **JSON Format**: Return the extracted data in JSON format. Follow the example provided to ensure accuracy in your response format:
   {example}
   
4. **Exclusivity and Precision**: Do not include any information that is not explicitly mentioned in the text. Analyze the text carefully to ensure all requested data is extracted accurately and included only once in your response.
5. **Adherence to Format**: Adhere strictly to the response format. Ensure that your JSON output does not contain extra spaces or text that deviates from the specified schema.

Your role is crucial in supporting informed decision-making in the agricultural sector by providing reliable, timely, and context-aware data extraction. Adapt and refine your extraction techniques based on feedback and evolving agricultural practices to maintain relevance and accuracy over time.
"""

def extract_objects(text, data_class):
    example = docstring_parser.parse(inspect.getdoc(data_class)).examples[0].description
    prompt = template.format(text=text, schema=json_schema(data_class), example=example)
    # print("*******************************")
    # print(prompt)
    # print("*******************************")
    output = model(prompt, verbose=True, json_mode=True)
    return from_json(data_class, output)

# Cell 4

from dataclasses import dataclass
from typing import Optional, Union


@dataclass
class PlantingEvent:
    """
    An entry of agricultural information mentioned in a conversation.

    Args:
        crop: The type of crop that was planted.
        date: The date when the crop was planted. This can include a specific date, a month, or just a year. The format may vary, and the field should capture the most specific date information available. If only a part of the date is mentioned (e.g., just the month or season), this should still be captured.

    Example:
        From a statement like "We planted corn in early May in Iowa using no-till," the extracted information should be:
        [{"crop": "corn", "date": "early May"}]
    """
    crop: Optional[str] = None
    date: Optional[str] = None





@dataclass
class Yield:
    """
    A measurement of yield.

    Args:
        amount: The numerical value representing the yield.
        units: The units used to measure the yield amount, incorporating relevant qualifiers that describe the yield's characteristics or specifications.
        description: Additional descriptors or specifications associated with the yield, such as weight or quality indicators.
    
    Example:
        For a statement like "We produced 5 barrels total. We are talking about 53 gallon barrels." the extracted information should be:
        [{"amount": "5", "units": "barrels", "description": "53 gallon barrels"}]
    """
    amount: Optional[str] = None
    units: Optional[str] = None
    description: Optional[str] = None


# Cell 5

# print(json_schema(PlantingEvent, indent=4))

# Cell 6

# This is interview 47

text = """
<speaker_1> Okay. Very cool. So the 2021 was probably grain sorghum then? 
<speaker_2> Yeah. 21 would've been grain sorghum. Yeah.
"""


start = time.time()
extract_objects(text, PlantingEvent)
end = time.time()
print("Time for tiny:", end - start)


file_47 = "../../corpora/47-short-BarryEvans.txt"
with open(file_47, encoding="utf8") as file: text_47 = file.read()
start = time.time()
extract_objects(text_47, PlantingEvent)
end = time.time()
print("Time for short:", end - start)


# Cell 7

# This is interview 61

text = """
<speaker_1> Yes.
<speaker_2> All right. And do you have a yield for that?
<speaker_1> That's usually all over the board, depending on how much moisture we get, but let's see. Try to hit 5 round bales to the acre for first cutting and that would be 800 pound bales. 2, trying to make my brain learn. 
<speaker_2> and we're giving you some math problems here.
"""

start = time.time()
extract_objects(text, PlantingEvent)
end = time.time()
print("Time for tiny:", end - start)


file_61 = "../../corpora/61-short-Wilds.txt"
with open(file_61, encoding="utf8") as file: text_61 = file.read()
start = time.time()
extract_objects(text_61, PlantingEvent)
end = time.time()
print("Time for short:", end - start)

# Cell 8

# This is interview 61

text = """
<speaker_1> Yes.
<speaker_2> All right. And do you have a yield for that?
<speaker_1> That's usually all over the board, depending on how much moisture we get, but let's see. Try to hit 5 round bales to the acre for first cutting and that would be 800 pound bales. 2, trying to make my brain learn. 
<speaker_2> and we're giving you some math problems here.
"""

start = time.time()
extract_objects(text, Yield)
end = time.time()
print("Time for tiny:", end - start)

start = time.time()
extract_objects(text_61, Yield)
end = time.time()
print("Time for short:", end - start)
