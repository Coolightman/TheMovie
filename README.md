# TheMovie

This application allows you to view lists of popular films and TOP-250 according to the "Kinopoisk".
A description of the movie include text information, frames, links to the videos, facts about the movie, reviews, similar movies and more. 

## Tech Stack

Kotlin, Android architecture components, Clean Architecture, Retrofit, Room, Dagger, Glide, Coroutine, Navigation, Single Activity App

## App screenshots

### Main screen
Here is used ViewPager2. Every page has infinite scrolling.
You can refresh pages by swipe down, or it will be refreshing by some time when page is opening. (Popular in 6 hours, Top-250 in 24 hours)

<img src="https://user-images.githubusercontent.com/43218153/149674837-d888eaf2-fdef-46b6-b977-a5ecea14b0e1.jpg" alt="drawing" width="270"/>

### Search screen

<img src="https://user-images.githubusercontent.com/43218153/149815394-5aa4ea7d-aed0-448d-a9a5-39e302b41676.jpg" alt="drawing" width="270"/>

### Movie details screen
Here you can see main parts of movie like a poster, some ratings, detail information, frames, facts, videos, review and similar movies. Every part is interactive.

<img src="https://user-images.githubusercontent.com/43218153/149675189-3eb4ce83-3991-4b10-9cb8-94aefda948e1.jpg" alt="drawing" width="270"/>

### From movie details, you can go, for example, to:
### Reviews
Click on some item is opening detail review. Color of card is showing review type(positive, negative or neutral).

<img src="https://user-images.githubusercontent.com/43218153/149674845-140298ff-00ab-4a31-bfa7-7adf565a75d9.jpg" alt="drawing" width="270"/>

### Gallery
It's opening when click on any frame on movie detail screen. Can rotate to landscape if need.

<img src="https://user-images.githubusercontent.com/43218153/149674846-d72a5d92-8359-499e-a5cf-6236cc024ca1.jpg" alt="drawing" width="270"/>

<img src="https://user-images.githubusercontent.com/43218153/149674847-b6f5a87e-fd87-4c70-9bb4-6ced2e82625d.jpg" alt="drawing" width="480"/>

### Favorite
Also you can add a movie to favorite, by click to star icon.

<img src="https://user-images.githubusercontent.com/43218153/149815403-d046d298-421c-472f-a5ca-c3ea487b8f78.jpg" alt="drawing" width="270"/>

It will appear on Favorite page of main screen

<img src="https://user-images.githubusercontent.com/43218153/149815415-37ec4f69-eb9c-4a61-966c-852aabe23093.jpg" alt="drawing" width="270"/>

And it will marked on other screens like favorite

<img src="https://user-images.githubusercontent.com/43218153/149816216-c6e86ef1-d902-4862-a6f6-b3cf60708f5f.jpg" alt="drawing" width="270"/>
