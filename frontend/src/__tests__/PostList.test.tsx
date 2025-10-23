import {render, screen} from "@testing-library/react";
import {PostList} from "../pages/PostList.tsx";
import {describe, it, expect} from "vitest";

describe('Post List page (main page)', () => {

    it('should show button "New post"', () => {
        render(<PostList/>)
        expect(screen.getByRole("button", { name: "New post"})).toBeVisible();
    })
})