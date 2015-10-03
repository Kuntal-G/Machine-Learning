# DATA CLEANING STEPS

# *** CHURN ANALYSIS ***

# Remove the white spaces from the file
sed -i 's/\s//g' churn.all

# Replace False. with False and True. with True
sed -i 's/False./False/g' churn.all
sed -i 's/True./True/g' churn.all


# Add the header line
sed -i '1s/^/state,account length,area code,phone number,international plan,voice mail plan,number vmail messages,total day minutes,total day calls,total day charge,total eve minutes,total eve calls,total eve charge,total night minutes,total night calls,total night charge,total intl minutes,total intl calls,total intl charge,number customer service calls,Status\n/' churn.all

# Clean the file to remove quotes and white spaces and replace NA introduced during the feature engineering phase with NA/0.
sed -i 's/"//g' churn_data_clean.all.csv
sed -i 's/NA/0/g' churn_data_clean.all.csv
sed -i 's/"//g' churn_data_clean_test.all.csv
sed -i 's/NA/0/g' churn_data_clean_test.all.csv


