print("hello from luaj")

local LEFT = "kp4"
local RIGHT = "kp6"
local UP = "kp8"
local DOWN = "kp2"

local WIDTH = love.graphics.getWidth()
local HEIGHT = love.graphics.getHeight()

local CUPERT_SIZE = 32

local scroll_speed = {x = 1, y = 1}

function love.load()
    print("load callback")
    love.system.vibrate()
    cupert_x = 0
    cupert_y = 0
    cupert = love.graphics.newImage("cupert.png")
    love.graphics.setBackgroundColor(1, 1, 1)
    print(cupert)
    local mt = getmetatable(cupert)
    print(mt)

    -- debugging for LÖVE behavior
    if not mt then return end
    for k, v in pairs(mt) do
        print(k, v)
    end
end

function love.update(dt)
    --scroll_speed.x = (love.keyboard.isDown(RIGHT) and 1 or 0) - (love.keyboard.isDown(LEFT) and 1 or 0)
    --scroll_speed.y = (love.keyboard.isDown(DOWN) and 1 or 0) - (love.keyboard.isDown(UP) and 1 or 0)
    if love.keyboard.isDown(RIGHT) then
        scroll_speed.x = 1
    elseif love.keyboard.isDown(LEFT) then
        scroll_speed.x = -1
    end

    if love.keyboard.isDown(DOWN) then
        scroll_speed.y = 1
    elseif love.keyboard.isDown(UP) then
        scroll_speed.y = -1
    end

    cupert_x = cupert_x + ((100*dt) * scroll_speed.x)
    cupert_y = cupert_y + ((100*dt) * scroll_speed.y)
    -- wrap around for checkerboard pattern
    cupert_x = cupert_x % (CUPERT_SIZE * 2)
    cupert_y = cupert_y % (CUPERT_SIZE * 2)
end

local function draw_cupert(x, y)
    local MAP_WIDTH = math.floor((WIDTH / CUPERT_SIZE) + 0.5) + 1
    local MAP_HEIGHT = math.floor((HEIGHT / CUPERT_SIZE) + 0.5) + 1
    -- make size even to make checkerboard pattern correct
    if (MAP_WIDTH % 2) ~= 0 then MAP_WIDTH = MAP_WIDTH + 1 end
    if (MAP_HEIGHT % 2) ~= 0 then MAP_HEIGHT = MAP_HEIGHT + 1 end
    local VIEW_WIDTH = MAP_WIDTH * CUPERT_SIZE
    local VIEW_HEIGHT = MAP_HEIGHT * CUPERT_SIZE

    for my = 0, MAP_HEIGHT, 1 do
        for mx = 0, MAP_WIDTH, 1 do
            if ((mx + my) % 2) == 0 then
                local fx = x + (mx * CUPERT_SIZE)
                local fy = y + (my * CUPERT_SIZE)
                fx = fx % VIEW_WIDTH
                fy = fy % VIEW_HEIGHT
                fx = fx - CUPERT_SIZE
                fy = fy - CUPERT_SIZE
                love.graphics.draw(cupert, fx, fy)
            end -- if
        end -- for mx
    end -- for my
end

function love.draw()
    draw_cupert(cupert_x, cupert_y)

    love.graphics.setColor(0, 0, 0)
    love.graphics.print("LöveME + " .. _VERSION, 20, 20)
    love.graphics.print("No game!", 20, 50)
    love.graphics.setColor(1, 1, 1)
end