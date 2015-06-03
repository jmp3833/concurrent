let cali_state_tax = 0.08;
let tip = 0.20;

let foods = {
  'rum_punch': 9,
  'chili_shrimp': 18,
  'margarita': 10,
  'tuna_wrap': 15,
  'steak_rice_bowl': 16,
  'steak_tacos': 16,
  'quinoa_burger': 12.5,
  'seasonal_soda': 4,
  'chicken_sausage_pizza': 13,
  'asparagus_pizza': 12.50,
  'green_arnie': 5,
  'panang_curry': 17,
  'hangover_rx': 5,
  'bison_burger': 16
};

let theGang = {
  'David': [foods.margarita, foods.chili_shrimp],
  'Kosi': [foods.chicken_sausage_pizza],
  'Jaydeep': [foods.quinoa_burger, foods.asparagus_pizza],
  'Erik': [foods.steak_tacos],
  'Katelyn': [foods.rum_punch, foods.chili_shrimp],
  'Zachery': [foods.chicken_sausage_pizza],
  'Joe': [foods.seasonal_soda, foods.chili_shrimp],
  'Ryan': [foods.steak_rice_bowl],
  'Justin': [foods.tuna_wrap, foods.margarita, foods.seasonal_soda],
  'Jaben': [foods.green_arnie, foods.panang_curry],
  'Sammy': [foods.hangover_rx],
  'Alfred': [foods.chicken_sausage_pizza],
  'Sam': [foods.hangover_rx, foods.bison_burger]
};

let sum = 0;

Object.keys(theGang).forEach((member) => {
  let individualTotal = theGang[member].reduce((total, foodItem) => {
    return total + foodItem;
  });

  individualTotal += (individualTotal * cali_state_tax);
  individualTotal += (individualTotal * tip);
  
  console.log('Total for ', member, ' is: ', individualTotal.toFixed(2));
  sum += individualTotal;
});

console.log("Total sum is: ", sum.toFixed(2));
