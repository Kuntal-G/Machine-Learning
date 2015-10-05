#Basic  HTML scrapping using Beautiful Soup
from bs4 import BeautifulSoup
html_markup = """<div class="ecopyramid">
<ul id="producers">
<li class="producerlist">
<div class="name">plants</div>
<div class="number">100000</div>
</li>
<li class="producerlist">
<div class="name">algae</div>
<div class="number">100000</div>
</li>
</ul>"""

soup = BeautifulSoup(html_markup,"lxml")
producer_entries = soup.ul
print(producer_entries.name)

#modifiying to ul

producer_entries.name = "div"
print(producer_entries.prettify())

#changing id attribute

producer_entries["id"] = "producers_new_value"
print(producer_entries.prettify())

#adding new class

producer_entries["class"] = "newclass"
print(producer_entries.prettify())

#deleting class attribute

del producer_entries["class"]
print(producer_entries.prettify())

#adding new tag
soup = BeautifulSoup(html_markup,"lxml")
new_li_tag = soup.new_tag("li")
new_li_tag.attrs = {"class":"producerlist"}
producer_entries = soup.ul
producer_entries.append(new_li_tag)
print(producer_entries.prettify())

#adding new tag using insert

new_div_name_tag = soup.new_tag("div")
new_div_name_tag["class"] = "name"
new_div_number_tag = soup.new_tag("div")
new_div_number_tag["class"] = "number"
new_li_tag.insert(0,new_div_name_tag)
new_li_tag.insert(1,new_div_number_tag)
print(new_li_tag.prettify())


#adding/modifying string

new_div_name_tag.string = "phytoplankton"
print(producer_entries.prettify())

#using append

new_div_name_tag.append("producer")
print(soup.prettify()) 

#using new_string

new_string_toappend = soup.new_string("producer")
new_div_name_tag.append(new_string_toappend)

#using insert
new_string_toinsert  = soup.new_string("10000")
new_div_number_tag.insert(0, new_string_toinsert)
print(soup.prettify())


#deleting using decompose

third_producer = soup.find_all("li")[2]
div_name = third_producer.div
div_name.decompose()
print(third_producer.prettify())

third_producer = soup.find_all("li")[2]
div_number= third_producer.div
div_number.decompose()
print(third_producer.prettify())

#using extract

third_producer_removed = third_producer.extract()
print(soup.prettify())

#using insert_after()

soup = BeautifulSoup(html_markup,"lxml")
div_number = soup.find("div",class_="number")
div_ecosystem = soup.new_tag("div")
div_ecosystem["class"] = "ecosystem"
div_ecosystem.append("soil")
div_number.insert_after(div_ecosystem)
print(soup.prettify())

#replace_with


div_name =soup.find("div",class_="name")
div_name.string.replace_with("phytoplankton")
print(soup.prettify())


#wrap

li_tags = soup.find_all("li")
for li in li_tags:
	new_divtag = soup.new_tag("div")
	li.wrap(new_divtag)
print(soup.prettify())

#unwrap

soup = BeautifulSoup(html_markup,"lxml")
li_tags = soup.find_all("li")
for li in li_tags:
	li.div.unwrap()  
print(soup.prettify())

