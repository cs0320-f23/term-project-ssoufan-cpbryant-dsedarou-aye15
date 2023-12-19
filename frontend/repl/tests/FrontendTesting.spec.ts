import { test, expect } from "@playwright/test";
/**
 * loads the page url before each test
 */
test.beforeEach(async ({ page }) => {
  await page.goto("http://localhost:8000/");
});

// Test to ensure that the calorie limit and dietary restriction filters work on Monday CSV
test("test on Monday CSV for 2000 calorie limit and Vegan restriction", async ({ page }) => {
  // Set calorie limit
  await page.getByPlaceholder("(Ex: 2500 calories)").click();
  await page.getByPlaceholder("(Ex: 2500 calories)").fill("2000");
  await page.getByRole("button", { name: "confirm" }).click();
  
  // Apply Vegan restriction
  await page.getByLabel("Vegan").check();
  
  // Verify that a specific menu item is visible
  await expect(page.getByRole("cell", { name: "VDub Vegan Buffalo Chicken" })).toBeVisible();
});

// Test to ensure that switching dietary restrictions works as expected
test("test on Monday CSV for 2000 calorie limit and Vegan restriction then 2000 Calorie Halal restriction", async ({ page }) => {
  // Set calorie limit and apply Vegan restriction
  await page.getByPlaceholder("(Ex: 2500 calories)").click();
  await page.getByPlaceholder("(Ex: 2500 calories)").fill("2000");
  await page.getByRole("button", { name: "confirm" }).click();
  await page.getByLabel("Vegan").check();
  
  // Switch to Halal restriction
  await page.getByRole("button", { name: "confirm" }).click();
  await page.locator("#Halal").check();
  
  // Verify that a specific menu item is visible
  await expect(page.getByRole("cell", { name: "VDub Omelet - 90 calories " })).toBeVisible();
});

// Test to check handling of invalid calorie input
test("test invalid calorie input", async ({ page }) => {
  // Enter invalid calorie input
  await page.getByPlaceholder("(Ex: 2500 calories)").click();
  await page.getByPlaceholder("(Ex: 2500 calories)").fill("dfdwjfklfjlkj");
  await page.getByRole("button", { name: "confirm" }).click();
  
  // Apply dietary restriction
  await page.getByLabel("Vegetarian").check();
  
  // Verify that a specific message is visible
  await expect(page.getByText("No items available")).toBeVisible();
});

// Test to check the functionality of the "Generate" button
test("test generate button", async ({ page }) => {
  // Click the "Generate" button
  await page.getByRole("button", { name: "Generate" }).click();
  
  // Verify that a specific text is visible
  await expect(page.getByText("Menu Day:")).toBeVisible();
});

// Test to check community section input and submission
test("test community section input", async ({ page }) => {
  // Enter a review in the community section
  await page.getByPlaceholder("(Enter your review here)").click();
  await page.getByPlaceholder("(Enter your review here)").fill("What does the Ratty have today?");
  await page.getByRole("button", { name: "Submit!" }).click();
  
  // Verify that the entered review text is visible
  await expect(page.getByText("What does the Ratty have")).toBeVisible();
});

// Test to check multiple community section inputs and submissions
test("test multiple community section input", async ({ page }) => {
  // Enter the first review in the community section
  await page.getByPlaceholder("(Enter your review here)").click();
  await page.getByPlaceholder("(Enter your review here)").fill("What does the Ratty have today?");
  await page.getByRole("button", { name: "Submit!" }).click();
  
  // Verify that the first entered review text is visible
  await expect(page.getByText("What does the Ratty have")).toBeVisible();
  
  // Enter the second review in the community section
  await page.getByPlaceholder("(Enter your review here)").click();
  await page.getByPlaceholder("(Enter your review here)").fill("The Ratty has Pizza!");
  await page.getByRole("button", { name: "Submit!" }).click();
  
  // Verify that the second entered review text is visible
  await expect(page.getByText("The Ratty has Pizza!")).toBeVisible();
});

// Test to check community post, generating the day, and then doing a meal input
test("test community post, generating the day, and then doing a meal input", async ({ page }) => {
  // Enter a review in the community section
  await page.getByPlaceholder("(Enter your review here)").click();
  await page.getByPlaceholder("(Enter your review here)").fill("What does the Ratty have today?");
  await page.getByRole("button", { name: "Submit!" }).click();
  
  // Click the "Generate" button
  await page.getByRole("button", { name: "Generate" }).click();
  
  // Set calorie limit and apply dietary restriction
  await page.getByPlaceholder("(Ex: 2500 calories)").click();
  await page.getByPlaceholder("(Ex: 2500 calories)").fill("3000");
  await page.getByRole("button", { name: "confirm" }).click();
  await page.getByLabel("None").check();
  
  // Verify that a specific menu item is visible
  await expect(page.getByRole("cell", { name: "Ratty Crinkle-Cut Fries - 360" })).toBeVisible();
});

// Test to check handling of no calorie input
test("checks to see that frontend properly handles no calorie input", async ({ page }) => {
  // Apply multiple dietary restrictions and click confirm button
  await page.getByLabel("Vegan").check();
  await page.getByRole("button", { name: "confirm" }).click();
  await page.getByLabel("Vegetarian").check();
  await page.getByRole("button", { name: "confirm" }).click();
  await page.getByLabel("None").check();
  await page.getByRole("button", { name: "confirm" }).click();
  await page.getByRole("button", { name: "confirm" }).click();
  
  // Click a specific text and verify the result
  await page.getByText("GlutenVegetarianVeganHalalNone").click();
  await expect(page.getByText("No items available")).toBeVisible();
});
// Test when popup button is clicked that it pops up
test("test popup menu", async ({ page }) => {
  await page
    .getByRole("button", { name: "Click Here to View the Menu!" })
    .click();
      await expect(
        page.getByRole("cell", { name: "Ratty Blueberry French Toast" })
      ).toBeVisible();

});
