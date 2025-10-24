import {render, screen} from "@testing-library/react";
import {PostList} from "../pages/PostList.tsx";
import {describe, expect, it} from "vitest";
import {userEvent} from "@testing-library/user-event";

describe('Post List page (main page)', () => {

    it('should show button "New post"', () => {
        render(<PostList/>);
        expect(screen.getByRole("button", { name: "New post"})).toBeVisible();
    })

    it('should not show dialog on load', () => {
        render(<PostList/>);
        expect(screen.queryByRole("dialog", { name: "New blog post"})).toBeNull();
    })

    it('should show modal if New Post button is clicked', async () => {
        const user = userEvent.setup();
        render(<PostList/>);
        await user.click(screen.getByRole("button", { name: "New post"}));
        expect(screen.getByLabelText("New blog post")).toBeVisible();
    })

    it('should close modal if close button is clicked', async () => {
        const user = userEvent.setup();
        render(<PostList/>);
        await user.click(screen.getByRole("button", { name: "New post"}));
        await user.click(screen.getByRole("button", {name: "Cancel"}));
        expect(screen.queryByRole("dialog", { name: "New blog post"})).toBeNull();
    })

    /*
    it('should use axios to make a GET call', async () => {
        const posts : BlogpostProps[] = [
            {   id: 1,
                timestamp: "",
                author: "author 1",
                subject: "subject 1",
                content: "content 1",
                likes: 1},
            {   id: 2,
                timestamp: "",
                author: "author 2",
                subject: "subject 2",
                content: "content 2",
                likes: 2}
            ]

        vi.spyOn(api, 'get').mockResolvedValueOnce(posts);
        render(<PostList/>);
        expect(await screen.findAllByRole('banner')).toHaveLength(2);
    })

     */


    // Should publish new post with mock HTTP response




/*

    it('should call axios POST to add a todo', async () => {
        const axiosSpy = vi.spyOn(axios, 'post');
        const testTodoToAdd: Todo = {
            name: "Test todo",
            description: "Test description",
            assignee: "Test person",
            points: 5,
            status: "incomplete"
        }
        await addTodo(testTodoToAdd);
        expect(axiosSpy).toHaveBeenCalledWith('/api/todo', testTodoToAdd);
    });

    it('should return created todo', async () => {
        const testTodoToAdd: Todo = {
            name: "Test todo",
            description: "Test description",
            assignee: "Test person",
            points: 5,
            status: "incomplete"
        }
        vi.spyOn(axios, 'post').mockResolvedValueOnce({ data: { ...testTodoToAdd, id: 1} });
        const result = await addTodo(testTodoToAdd);
        expect(result).toEqual({...testTodoToAdd, id: 1})
    });

 */
})