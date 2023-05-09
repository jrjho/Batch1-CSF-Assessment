import { HttpClient, HttpErrorResponse, HttpHeaders, HttpParams } from '@angular/common/http';
import { Component, OnDestroy, OnInit, Output } from '@angular/core';
import { Router, ActivatedRoute } from '@angular/router';
import { Subject, Subscription, firstValueFrom, lastValueFrom, map } from 'rxjs';
import { Review } from '../../models/Review';

@Component({
  selector: 'app-results',
  templateUrl: './results.component.html',
  styleUrls: ['./results.component.css']
})
export class ResultsComponent implements OnInit, OnDestroy{

  private API_URL = 'http://localhost:8080/api/search'
  private API_RAILWAY = 'https://tiny-direction-production.up.railway.app/api/search'
  private headers = new HttpHeaders()
  .set("Content-Type", "application/json ; charset=utf-8");

  param$!: Subscription
  query!: string
  reviews: Review[]=[]
  movieProm$!: Promise<Review[]>


  // @Output()
  // onQuery = new Subject<string>()

  constructor(private router : Router, private activatedRoute : ActivatedRoute, private httpClient: HttpClient ) {}

  
  ngOnInit(): void {
    console.log("results init")

    this.param$ = this.activatedRoute.queryParams.subscribe(
       (inputMovieName) => {
        this.query = inputMovieName['query']
      }

    )
    console.log("results query: ",this.query)


    //Method 1----------------------------------------
    // this.getMovies(this.query.toLowerCase()).then(
    //   (data:any) => {
    //     //'results is the result returned by NYT API
    //     this.reviews = data['results'] as Review[]
    //     console.info("this.reviews: ",this.reviews)
    //   }
    // ).catch(
    //   (error : HttpErrorResponse) => {
    //     this.reviews = []
    //   }
    // )
    //--------------------------------


    //Method 2----------------------------------------
    this.movieProm$ = lastValueFrom(
      // returns an observable and use lastValue to convert it into a promise
    this.getMovies(this.query.toLowerCase())
    )
        console.log("MovieProm: ",this.movieProm$)
    //--------------------------------

  }

  //Method 2----------------------------------------
  getMovies(inputName: string){
    const params = new HttpParams().set("query", inputName.toLowerCase())
    const result = lastValueFrom(this.httpClient.get<Review>(this.API_URL,{params,headers: this.headers}));
    console.log(">>Results: ",result)
    // return result

    return this.httpClient.get<Review[]>(this.API_URL, { params })
      .pipe(
        map((v: any) => {
   
          const info = v['results'] as any[]
          return info.map(w => {
            return {
              title : w['title'],
              rating: w['rating'],
              byline: w['byline'],
              headline: w['headline'],
              summary: w['summary'],
              reviewUrl: w['reviewUrl'],
              image: w['image'],
              commentCount: w['commentCount']
            } as Review
          },
          this.reviews = info
          )

        })
      )
  }
  //--------------------------------

  //Method 1----------------------------------------
  // getMovies(inputName: string):Promise<any> {
  //   const params = new HttpParams().set("query", inputName.toLowerCase())
  //   return firstValueFrom(this.httpClient.get<string>(this.API_URL, {params:params,headers: this.headers}))
  //   // return firstValueFrom(this.httpClient.get<string>(this.API_RAILWAY, {params}))
  // }
  // ----------------------------------------

  ngOnDestroy(): void {
    this.param$.unsubscribe()
  }

  goBack() {
    this.router.navigate(["/"])
  }

  goToLink(idx : number) {
    const link = this.reviews[idx].reviewUrl
    window.open(link, '_blank')
    this.router.navigate(['/search'], {queryParams: {query: this.query}})
  }
  goComment(idx : number) {
    console.info("index is: ",idx)
    console.info("review[idx] is: ",this.reviews[idx])

    const movie = this.reviews[idx].title
    // console.info(movie)
    this.router.navigate(['/comment', movie], {queryParams: {query: this.query}})
  }
}
