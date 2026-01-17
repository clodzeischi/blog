import './index.css'
import {createBrowserRouter, RouterProvider} from "react-router-dom";
import {createRoot} from "react-dom/client";
import {allPostsLoader, AllPostsPage, createPostAction} from "./routes/posts/AllPostsPage.tsx";
import {ErrorPage} from "./routes/error/ErrorPage.tsx";
import {editPostAction, postLoader, PostViewPage} from "./routes/post/PostViewPage.tsx";
import {RootLayout} from "./routes/root/RootLayout.tsx";

const router = createBrowserRouter([
    {
        path: "/",
        element: <RootLayout />,
        errorElement: <ErrorPage />,
        children: [
            {
                index: true,
                element: <AllPostsPage />,
                loader: allPostsLoader,
                action: createPostAction,
            },
            {
                path: "posts/:postId",
                element: <PostViewPage />,
                loader: postLoader,
                action: editPostAction,
            },
        ],
    },
])

createRoot(document.getElementById("root")!).render(
    <RouterProvider router={router} />
)

