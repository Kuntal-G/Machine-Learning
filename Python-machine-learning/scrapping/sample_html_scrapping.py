#This script contains examples for searching methods in Beautiful Soup
from bs4 import BeautifulSoup
import urllib2
import re
#ecological pyramida example

with open("ecologicalpyramid.html","r") as ecological_pyramid: 
	soup = BeautifulSoup(ecological_pyramid,"lxml")
producer_entries = soup.find("ul")
print(producer_entries.li.div.string)

tag_li  = soup.find("li")
print(type(tag_li))

#search for text
search_for_stringonly = soup.find(text="fox")
print(search_for_stringonly)

#finding css class using attr
css_class = soup.find(attrs={"class":"primaryconsumerlist"})
print(css_class)
#finding css class using class_

css_class = soup.find(class_ = "primaryconsumers" ) 

#find using pre defined functions

def is_secondary_consumers(tag):
	return tag.has_attr("id") and tag.get("id") == "secondaryconsumers"
	
secondary_consumer =  soup.find(is_secondary_consumers)
print(secondary_consumer.li.div.string)


#example for find_all
all_tertiaryconsumers = soup.find_all(class_="tertiaryconsumerslist")
print(type(all_tertiaryconsumers))

#searching all text

all_texts = soup.find_all(text=True)
print(all_texts)

#searching group of string

all_texts_in_list = soup.find_all(text=["plants","algae"])
print(all_texts_in_list)

#findign all div and li tags

div_li_tags = soup.find_all(["div","li"])

#findign all css classes

all_css_class = soup.find_all(class_=["producerlist","primaryconsumerlist"])

#find all using recursive

div_li_tags = soup.find_all(["div","li"],recursive=False)
print(div_li_tags)


#Regular expression example.

email_id_example = """<br/>
<div>The below HTML has the information that has email ids.</div> 
abc@example.com
<div>xyz@example.com</div>
<span>foo@example.com</span>
 """
soup = BeautifulSoup(email_id_example,"lxml")
emailid_regexp = re.compile("\w+@\w+\.\w+")
first_email_id = soup.find(text=emailid_regexp) 
print(first_email_id)

#searching using find_all based on reg exp

email_ids = soup.find_all(text=emailid_regexp)
print(email_ids) 

#using limt in find_all

email_ids_limited = soup.find_all(text=emailid_regexp,limit=2)
print(email_ids_limited)

#using custom attrs

customattr = """<p data-custom="custom">custom attribute example</p>"""
customsoup = BeautifulSoup(customattr,"lxml")
using_attrs = customsoup.find(attrs={"data-custom":"custom"})
print(using_attrs)

#searching for combinations

html_identical = """<p class="identical">
Example of p tag with class identical
</p>
<div class="identical">
Example of div tag with class identical
</div>"""
soup=BeautifulSoup(html_identical,"lxml")
identical_div= soup.find("div",class_="identical")
print(identical_div)

#scraper for packtpub example

import urllib2
from bs4 import BeautifulSoup
url = "http://www.packtpub.com/books"
page  = urllib2.urlopen(url)
soup_packtpage = BeautifulSoup(page)
page.close()

all_books_table = soup_packtpage.find("table",class_="views-view-grid")

all_book_titles = all_books_table.find_all("div",class_="views-field-title")


for book_title in all_book_titles:
	book_title_span = book_title.span
	print("Title Name is :"+book_title_span.a.string)
	published_date = book_title.find_next("div",class_="views-field-field-date-of-publication-value")
	print("Published Date is :"+published_date.span.string)
	price = book_title.find_next("div",class_="views-field-sell-price")
	print("Price is :"+price.span.string)

